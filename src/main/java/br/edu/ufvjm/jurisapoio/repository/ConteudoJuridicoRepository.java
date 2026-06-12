package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.ConteudoJuridico;
import br.edu.ufvjm.jurisapoio.enums.CategoriaConteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConteudoJuridicoRepository extends JpaRepository<ConteudoJuridico, UUID> {

    List<ConteudoJuridico> findAllByPublicadoTrueOrderByDataPublicacaoDesc();

    List<ConteudoJuridico> findAllByCategoriaAndPublicadoTrue(CategoriaConteudo categoria);
}
