package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.Caso;
import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CasoRepository extends JpaRepository<Caso, UUID> {

    // RN01: verifica se vítima já tem caso ativo
    boolean existsByVitimaIdAndStatusIn(UUID vitimaId, List<StatusCaso> statusAtivos);

    Optional<Caso> findByVitimaIdAndStatusIn(UUID vitimaId, List<StatusCaso> statusAtivos);

    List<Caso> findAllByAdvogadoVoluntarioId(UUID advogadoId);

    // RN10: conta casos ativos do advogado
    long countByAdvogadoVoluntarioIdAndStatusIn(UUID advogadoId, List<StatusCaso> statusAtivos);
}
