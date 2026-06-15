package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.AdvogadoCadastroRequest;
import br.edu.ufvjm.jurisapoio.dto.request.CadastroVitimaRequest;
import br.edu.ufvjm.jurisapoio.dto.request.LoginRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AuthResponse;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.entity.RefreshToken;
import br.edu.ufvjm.jurisapoio.entity.Usuario;
import br.edu.ufvjm.jurisapoio.entity.Vitima;
import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import br.edu.ufvjm.jurisapoio.repository.RefreshTokenRepository;
import br.edu.ufvjm.jurisapoio.repository.UsuarioRepository;
import br.edu.ufvjm.jurisapoio.repository.VitimaRepository;
import br.edu.ufvjm.jurisapoio.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final VitimaRepository vitimaRepository;
    private final AdvogadoVoluntarioRepository advogadoVoluntarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder codificadorSenha;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager gerenciadorAutenticacao;
    private final UserDetailsService userDetailsService;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        gerenciadorAutenticacao.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String accessToken = jwtUtil.gerarToken(userDetails);

        Usuario usuario = usuarioRepository.findByEmailAndAtivoTrue(request.email())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        String refreshToken = criarRefreshToken(usuario);
        return new AuthResponse(accessToken, refreshToken, derivarPerfil(usuario), derivarIdentificador(usuario));
    }

    @Transactional
    public AuthResponse cadastrarVitima(CadastroVitimaRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado");
        }
        if (Boolean.FALSE.equals(request.aceitouTermos())) {
            throw new BusinessException("É necessário aceitar os termos de uso");
        }

        Vitima vitima = Vitima.builder()
                .email(request.email())
                .senhaHash(codificadorSenha.encode(request.senha()))
                .nomeAnonimo(request.nomeAnonimo())
                .estadoResidencia(request.estadoResidencia())
                .aceitouTermos(request.aceitouTermos())
                .build();
        vitima = vitimaRepository.save(vitima);

        UserDetails userDetails = userDetailsService.loadUserByUsername(vitima.getEmail());
        String accessToken = jwtUtil.gerarToken(userDetails);
        String refreshToken = criarRefreshToken(vitima);
        return new AuthResponse(accessToken, refreshToken, PerfilUsuario.VITIMA, vitima.getNomeAnonimo());
    }

    @Transactional
    public AuthResponse cadastrarAdvogado(AdvogadoCadastroRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado");
        }
        if (advogadoVoluntarioRepository.existsByNumeroOAB(request.numeroOAB())) {
            throw new BusinessException("Número OAB já cadastrado");
        }

        AdvogadoVoluntario advogado = AdvogadoVoluntario.builder()
                .email(request.email())
                .senhaHash(codificadorSenha.encode(request.senha()))
                .nome(request.nome())
                .numeroOAB(request.numeroOAB())
                .especialidades(request.especialidades())
                .build();
        advogado = advogadoVoluntarioRepository.save(advogado);

        UserDetails userDetails = userDetailsService.loadUserByUsername(advogado.getEmail());
        String accessToken = jwtUtil.gerarToken(userDetails);
        String refreshToken = criarRefreshToken(advogado);
        return new AuthResponse(accessToken, refreshToken, PerfilUsuario.ADVOGADO_VOLUNTARIO, advogado.getNome());
    }

    @Transactional
    public AuthResponse renovarToken(String tokenStr) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenAndRevogadoFalse(tokenStr)
                .orElseThrow(() -> new BusinessException("Refresh token inválido ou expirado"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshToken.setRevogado(true);
            refreshTokenRepository.save(refreshToken);
            throw new BusinessException("Refresh token inválido ou expirado");
        }

        Usuario usuario = refreshToken.getUsuario();
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String novoAccessToken = jwtUtil.gerarToken(userDetails);

        return new AuthResponse(novoAccessToken, tokenStr, derivarPerfil(usuario), derivarIdentificador(usuario));
    }

    private String criarRefreshToken(Usuario usuario) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .usuario(usuario)
                .build();
        return refreshTokenRepository.save(refreshToken).getToken();
    }

    private PerfilUsuario derivarPerfil(Usuario usuario) {
        if (usuario instanceof Vitima) return PerfilUsuario.VITIMA;
        if (usuario instanceof AdvogadoVoluntario) return PerfilUsuario.ADVOGADO_VOLUNTARIO;
        return PerfilUsuario.ADMIN;
    }

    private String derivarIdentificador(Usuario usuario) {
        if (usuario instanceof Vitima v) return v.getNomeAnonimo();
        if (usuario instanceof AdvogadoVoluntario a) return a.getNome();
        return usuario.getEmail();
    }
}