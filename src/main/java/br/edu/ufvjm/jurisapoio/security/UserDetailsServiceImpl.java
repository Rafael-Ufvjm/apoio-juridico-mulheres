package br.edu.ufvjm.jurisapoio.security;

import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.entity.Administrador;
import br.edu.ufvjm.jurisapoio.entity.Usuario;
import br.edu.ufvjm.jurisapoio.entity.Vitima;
import br.edu.ufvjm.jurisapoio.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailAndAtivoTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenhaHash())
                .roles(derivarPerfil(usuario))
                .build();
    }

    private String derivarPerfil(Usuario usuario) {
        if (usuario instanceof Vitima) return "VITIMA";
        if (usuario instanceof AdvogadoVoluntario) return "ADVOGADO_VOLUNTARIO";
        if (usuario instanceof Administrador) return "ADMIN";
        return "VITIMA";
    }
}
