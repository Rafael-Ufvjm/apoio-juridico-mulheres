package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.DocumentoProva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentoProvaRepository extends JpaRepository<DocumentoProva, UUID> {

    List<DocumentoProva> findAllByCasoId(UUID casoId);

    // RN08: conta documentos por caso para limitar a 20
    long countByCasoId(UUID casoId);
}
