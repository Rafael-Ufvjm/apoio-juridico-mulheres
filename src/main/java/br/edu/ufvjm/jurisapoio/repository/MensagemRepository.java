package br.edu.ufvjm.jurisapoio.repository;

import br.edu.ufvjm.jurisapoio.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {

    List<Mensagem> findAllByCasoIdOrderByDataEnvioAsc(UUID casoId);

    // RN13: remove conteúdo de todas as mensagens ao encerrar o caso
    @Modifying
    @Query("UPDATE Mensagem m SET m.conteudoCriptografado = NULL, m.conteudoRemovido = TRUE WHERE m.caso.id = :casoId")
    void removerConteudoPorCasoId(UUID casoId);
}
