package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.MensagemRequest;
import br.edu.ufvjm.jurisapoio.dto.response.MensagemResponse;
import br.edu.ufvjm.jurisapoio.entity.Caso;
import br.edu.ufvjm.jurisapoio.entity.Mensagem;
import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;
import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.CasoRepository;
import br.edu.ufvjm.jurisapoio.repository.MensagemRepository;
import br.edu.ufvjm.jurisapoio.util.CriptografiaUtil;
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
        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso não encontrado com ID: " + casoId));

        if (caso.getStatus() == StatusCaso.ENCERRADO || caso.getStatus() == StatusCaso.ARQUIVADO) {
            throw new BusinessException("Não é possível enviar mensagens em um caso encerrado ou arquivado.");
        }

        boolean isVitima = caso.getVitima().getId().equals(remetenteId);
        boolean isAdvogado = caso.getAdvogadoVoluntario() != null
                && caso.getAdvogadoVoluntario().getId().equals(remetenteId);

        if (!isVitima && !isAdvogado) {
            throw new BusinessException("Você não é participante deste caso.");
        }

        String conteudoCriptografado = CriptografiaUtil.cifrar(request.conteudo(), caso.getChaveCriptografiaChat());

        Mensagem mensagem = Mensagem.builder()
                .caso(caso)
                .remetentePerfil(remetentePerfil)
                .conteudoCriptografado(conteudoCriptografado)
                .build();

        return mapearParaResponse(mensagemRepository.save(mensagem), caso.getChaveCriptografiaChat());
    }

    @Transactional(readOnly = true)
    public List<MensagemResponse> listarMensagensCaso(UUID casoId, UUID solicitanteId) {
        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso não encontrado com ID: " + casoId));

        boolean isVitima = caso.getVitima().getId().equals(solicitanteId);
        boolean isAdvogado = caso.getAdvogadoVoluntario() != null
                && caso.getAdvogadoVoluntario().getId().equals(solicitanteId);

        if (!isVitima && !isAdvogado) {
            throw new BusinessException("Você não é participante deste caso.");
        }

        String chave = caso.getChaveCriptografiaChat();
        return mensagemRepository.findAllByCasoIdOrderByDataEnvioAsc(casoId)
                .stream()
                .map(m -> mapearParaResponse(m, chave))
                .toList();
    }

    public MensagemResponse mapearParaResponse(Mensagem mensagem, String chaveChat) {
        String conteudo = null;
        if (!Boolean.TRUE.equals(mensagem.getConteudoRemovido()) && mensagem.getConteudoCriptografado() != null) {
            conteudo = CriptografiaUtil.decifrar(mensagem.getConteudoCriptografado(), chaveChat);
        }
        return new MensagemResponse(
                mensagem.getId(),
                mensagem.getRemetentePerfil(),
                conteudo,
                mensagem.getDataEnvio(),
                mensagem.getConteudoRemovido()
        );
    }
}