package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.Vitima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VitimaRepository extends JpaRepository<Vitima, UUID> {

    Optional<Vitima> findByEmail(String email);
}
