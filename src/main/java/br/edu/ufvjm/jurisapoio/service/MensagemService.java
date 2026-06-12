package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.MensagemRequest;
import br.edu.ufvjm.jurisapoio.dto.response.MensagemResponse;
import br.edu.ufvjm.jurisapoio.entity.Mensagem;
import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;
import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.repository.CasoRepository;
import br.edu.ufvjm.jurisapoio.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final CasoRepository casoRepository;

    @Transactional
    public MensagemResponse enviarMensagem(UUID casoId, UUID remetenteId, PerfilUsuario remetentePerfil, MensagemRequest request) {
        // TODO: Buscar Caso pelo casoId ou lançar ResourceNotFoundException.
        //       Verificar que caso.status != ENCERRADO nem ARQUIVADO.
        //       Verificar que remetenteId é participante do caso (vitima ou advogado).
        //       RN13: conteudoCriptografado: cifrar request.conteudo() via CriptografiaUtil.cifrar()
        //             usando caso.chaveCriptografiaChat como chave.
        //       Criar e persistir Mensagem com remetentePerfil e conteudoCriptografado.
        //       Retornar MensagemResponse com conteúdo decifrado (apenas para quem enviou/recebeu).
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional(readOnly = true)
    public List<MensagemResponse> listarMensagensCaso(UUID casoId, UUID solicitanteId) {
        // TODO: Buscar Caso pelo casoId ou lançar ResourceNotFoundException.
        //       Verificar participação do solicitante no caso.
        //       Buscar mensagens via mensagemRepository.findAllByCasoIdOrderByDataEnvioAsc().
        //       Para cada mensagem com conteudoRemovido = false, decifrar conteudo via CriptografiaUtil.decifrar().
        //       RN13: mensagens com conteudoRemovido = true retornam conteudo = null.
        //       Retornar lista de MensagemResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    public MensagemResponse mapearParaResponse(Mensagem mensagem, String chaveChat) {
        // TODO: Decifrar conteudoCriptografado se conteudoRemovido = false.
        //       Montar MensagemResponse com conteúdo decifrado (ou null se removido).
        throw new UnsupportedOperationException("Não implementado");
    }
}
