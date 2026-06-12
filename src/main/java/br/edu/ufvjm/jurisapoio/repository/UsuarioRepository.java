package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmailAndAtivoTrue(String email);

    boolean existsByEmail(String email);
}
