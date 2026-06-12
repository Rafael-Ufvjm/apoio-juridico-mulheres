package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByTokenAndRevogadoFalse(String token);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.revogado = TRUE WHERE r.usuario.id = :usuarioId")
    void revogarTodosPorUsuario(UUID usuarioId);
}
