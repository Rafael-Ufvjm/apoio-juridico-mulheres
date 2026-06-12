package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.AdvogadoCadastroRequest;
import br.edu.ufvjm.jurisapoio.dto.request.CadastroVitimaRequest;
import br.edu.ufvjm.jurisapoio.dto.request.LoginRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AuthResponse;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import br.edu.ufvjm.jurisapoio.repository.UsuarioRepository;
import br.edu.ufvjm.jurisapoio.repository.VitimaRepository;
import br.edu.ufvjm.jurisapoio.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final VitimaRepository vitimaRepository;
    private final AdvogadoVoluntarioRepository advogadoVoluntarioRepository;
    private final PasswordEncoder codificadorSenha;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager gerenciadorAutenticacao;
    private final UserDetailsService userDetailsService;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        // TODO: Autenticar via gerenciadorAutenticacao com UsernamePasswordAuthenticationToken.
        //       Carregar UserDetails pelo email após autenticação.
        //       Gerar accessToken via jwtUtil.gerarToken() e refreshToken via RefreshToken entity.
        //       Buscar Usuario no banco para derivar perfil e identificador (nomeAnonimo ou nome).
        //       Retornar AuthResponse(accessToken, refreshToken, perfil, identificador).
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public AuthResponse cadastrarVitima(CadastroVitimaRequest request) {
        // TODO: Verificar se email já existe via usuarioRepository.existsByEmail().
        //       Se sim, lançar BusinessException("Email já cadastrado").
        //       RN02: NÃO coletar nome real nem CPF — apenas nomeAnonimo.
        //       Criar Vitima com senhaHash = codificadorSenha.encode(request.senha()).
        //       Persistir e gerar tokens. Retornar AuthResponse com PerfilUsuario.VITIMA.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public AuthResponse cadastrarAdvogado(AdvogadoCadastroRequest request) {
        // TODO: Verificar se email já existe.
        //       Verificar se numeroOAB já existe via advogadoVoluntarioRepository.existsByNumeroOAB().
        //       Criar AdvogadoVoluntario com statusAprovacao = PENDENTE (via @PrePersist).
        //       Persistir e gerar tokens. Retornar AuthResponse com PerfilUsuario.ADVOGADO_VOLUNTARIO.
        //       ATENÇÃO: advogado PENDENTE não pode acessar casos (verificado em CasoService / SecurityConfig).
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public AuthResponse renovarToken(String refreshToken) {
        // TODO: Buscar RefreshToken pelo token via refreshTokenRepository.findByTokenAndRevogadoFalse().
        //       Se não encontrado ou expirado, lançar BusinessException("Refresh token inválido ou expirado").
        //       Gerar novo accessToken para o usuário associado.
        //       Retornar AuthResponse com novo accessToken (refreshToken pode ser o mesmo ou rotacionado).
        throw new UnsupportedOperationException("Não implementado");
    }
}
