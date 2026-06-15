package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvogadoVoluntarioRepository extends JpaRepository<AdvogadoVoluntario, UUID> {

    List<AdvogadoVoluntario> findAllByStatusAprovacao(StatusAprovacao status);

    boolean existsByNumeroOAB(String numeroOAB);

    Optional<AdvogadoVoluntario> findByEmail(String email);
}
