package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, UUID> {
}
