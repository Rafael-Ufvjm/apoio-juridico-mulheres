package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.CasoTriagemRequest;
import br.edu.ufvjm.jurisapoio.dto.request.EncerrarCasoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.CasoResponse;
import br.edu.ufvjm.jurisapoio.entity.Caso;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import br.edu.ufvjm.jurisapoio.repository.CasoRepository;
import br.edu.ufvjm.jurisapoio.repository.MensagemRepository;
import br.edu.ufvjm.jurisapoio.repository.VitimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CasoService {

    private static final int MAX_CASOS_ATIVOS_ADVOGADO = 5;
    private static final List<StatusCaso> STATUS_ATIVOS = List.of(StatusCaso.AGUARDANDO, StatusCaso.EM_ATENDIMENTO);

    private final CasoRepository casoRepository;
    private final VitimaRepository vitimaRepository;
    private final AdvogadoVoluntarioRepository advogadoVoluntarioRepository;
    private final MensagemRepository mensagemRepository;

    @Transactional
    public CasoResponse abrirCaso(UUID vitimaId, CasoTriagemRequest request) {
        // TODO: RN01: verificar se vítima já tem caso ativo via casoRepository.existsByVitimaIdAndStatusIn().
        //       Se sim, lançar BusinessException("Você já possui um caso ativo. Encerre-o antes de abrir um novo.").
        //       Buscar Vitima pelo vitimaId ou lançar ResourceNotFoundException.
        //       Gerar protocolo único (ex: "JA-" + ano + UUID curto).
        //       Gerar chaveCriptografiaChat via CriptografiaUtil.gerarChaveBase64() para proteger o canal.
        //       Criar Caso com vitima, tipoViolencia, descricao e status = AGUARDANDO (via @PrePersist).
        //       Persistir e retornar CasoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public CasoResponse atribuirAdvogado(UUID casoId, UUID advogadoId) {
        // TODO: Buscar Caso pelo casoId ou lançar ResourceNotFoundException.
        //       Verificar que caso.status == AGUARDANDO, caso contrário BusinessException.
        //       Buscar AdvogadoVoluntario ou lançar ResourceNotFoundException.
        //       Verificar que advogado.statusAprovacao == ATIVO, caso contrário BusinessException.
        //       RN10: contar casos ativos do advogado via casoRepository.countByAdvogadoVoluntarioIdAndStatusIn().
        //       Se count >= MAX_CASOS_ATIVOS_ADVOGADO (5), lançar BusinessException("Advogado já possui 5 casos ativos.").
        //       Atribuir advogado, mudar status para EM_ATENDIMENTO, incrementar advogado.totalCasosAtivos.
        //       Persistir e retornar CasoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public CasoResponse encerrarCaso(UUID casoId, UUID solicitanteId, EncerrarCasoRequest request) {
        // TODO: Buscar Caso pelo casoId ou lançar ResourceNotFoundException.
        //       Verificar que solicitanteId é a vítima ou o advogado do caso.
        //       Verificar que caso.status != ENCERRADO nem ARQUIVADO.
        //       RN12: request.resultado() é @NotBlank — apenas persistir.
        //       Setar caso.resultado, caso.status = ENCERRADO, caso.timestampEncerramento = now().
        //       RN13: chamar mensagemRepository.removerConteudoPorCasoId(casoId).
        //       Decrementar advogado.totalCasosAtivos se advogado atribuído.
        //       Persistir e retornar CasoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional(readOnly = true)
    public CasoResponse buscarPorId(UUID casoId) {
        // TODO: Buscar Caso pelo casoId ou lançar ResourceNotFoundException.
        //       Mapear para CasoResponse e retornar.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional(readOnly = true)
    public List<CasoResponse> listarCasosAdvogado(UUID advogadoId) {
        // TODO: Listar todos os casos do advogado via casoRepository.findAllByAdvogadoVoluntarioId().
        //       Mapear cada Caso para CasoResponse e retornar lista.
        //       RN02: CasoResponse.vitima deve expor apenas nomeAnonimo — nunca email.
        throw new UnsupportedOperationException("Não implementado");
    }

    public CasoResponse mapearParaResponse(Caso caso) {
        // TODO: Converter entidade Caso em CasoResponse.
        //       RN02: incluir VitimaResponse apenas com id, nomeAnonimo, estadoResidencia, dataCadastro.
        //       Incluir AdvogadoResponse se advogadoVoluntario != null.
        throw new UnsupportedOperationException("Não implementado");
    }
}
