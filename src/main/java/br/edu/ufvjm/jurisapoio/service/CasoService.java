package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.CasoTriagemRequest;
import br.edu.ufvjm.jurisapoio.dto.request.EncerrarCasoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.dto.response.CasoResponse;
import br.edu.ufvjm.jurisapoio.dto.response.VitimaResponse;
import br.edu.ufvjm.jurisapoio.entity.Administrador;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.entity.Caso;
import br.edu.ufvjm.jurisapoio.entity.Mensagem;
import br.edu.ufvjm.jurisapoio.entity.Usuario;
import br.edu.ufvjm.jurisapoio.entity.Vitima;
import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import br.edu.ufvjm.jurisapoio.repository.CasoRepository;
import br.edu.ufvjm.jurisapoio.repository.MensagemRepository;
import br.edu.ufvjm.jurisapoio.repository.UsuarioRepository;
import br.edu.ufvjm.jurisapoio.repository.VitimaRepository;
import br.edu.ufvjm.jurisapoio.util.CriptografiaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
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
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CasoResponse abrirCaso(UUID vitimaId, CasoTriagemRequest request) {
        // RN01: verificar se vítima já tem caso ativo
        if (casoRepository.existsByVitimaIdAndStatusIn(vitimaId, STATUS_ATIVOS)) {
            throw new BusinessException("Você já possui um caso ativo. Encerre-o antes de abrir um novo.");
        }

        Vitima vitima = vitimaRepository.findById(vitimaId)
                .orElseThrow(() -> new ResourceNotFoundException("Vítima não encontrada com ID: " + vitimaId));

        // RN07: Gerar protocolo único (ex: "JA-" + ano + "-" + UUID curto)
        String uuidCurto = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String protocolo = "JA-" + Year.now().getValue() + "-" + uuidCurto;

        // Gerar chaveCriptografiaChat via CriptografiaUtil
        String chaveCriptografia = CriptografiaUtil.gerarChaveBase64();

        Caso caso = Caso.builder()
                .protocolo(protocolo)
                .tipoViolencia(request.tipoViolencia())
                .status(StatusCaso.AGUARDANDO)
                .vitima(vitima)
                .chaveCriptografiaChat(chaveCriptografia)
                .build();

        Caso casoSalvo = casoRepository.save(caso);

        // Criptografar a descrição inicial e persistir como a primeira mensagem
        String descricaoCriptografada = CriptografiaUtil.cifrar(request.descricao(), chaveCriptografia);
        Mensagem mensagemDescricao = Mensagem.builder()
                .caso(casoSalvo)
                .remetentePerfil(PerfilUsuario.VITIMA)
                .conteudoCriptografado(descricaoCriptografada)
                .conteudoRemovido(false)
                .build();
        mensagemRepository.save(mensagemDescricao);

        return mapearParaResponse(casoSalvo);
    }

    @Transactional
    public CasoResponse atribuirAdvogado(UUID casoId, UUID advogadoId) {
        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso não encontrado com ID: " + casoId));

        if (caso.getStatus() != StatusCaso.AGUARDANDO) {
            throw new BusinessException("Este caso não está aguardando atendimento.");
        }

        AdvogadoVoluntario advogado = advogadoVoluntarioRepository.findById(advogadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Advogado não encontrado com ID: " + advogadoId));

        if (advogado.getStatusAprovacao() != StatusAprovacao.ATIVO) {
            throw new BusinessException("Apenas advogados com perfil aprovado (ATIVO) podem receber casos.");
        }

        // RN10: contar casos ativos do advogado
        long countAtivos = casoRepository.countByAdvogadoVoluntarioIdAndStatusIn(advogadoId, STATUS_ATIVOS);
        if (countAtivos >= MAX_CASOS_ATIVOS_ADVOGADO) {
            throw new BusinessException("Advogado já possui 5 casos ativos.");
        }

        caso.setAdvogadoVoluntario(advogado);
        caso.setStatus(StatusCaso.EM_ATENDIMENTO);
        advogado.setTotalCasosAtivos(advogado.getTotalCasosAtivos() + 1);

        advogadoVoluntarioRepository.save(advogado);
        Caso casoSalvo = casoRepository.save(caso);

        return mapearParaResponse(casoSalvo);
    }

    @Transactional
    public CasoResponse encerrarCaso(UUID casoId, UUID solicitanteId, EncerrarCasoRequest request) {
        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso não encontrado com ID: " + casoId));

        // Verificar que solicitanteId é a vítima ou o advogado do caso
        boolean isVitima = caso.getVitima().getId().equals(solicitanteId);
        boolean isAdvogado = caso.getAdvogadoVoluntario() != null && caso.getAdvogadoVoluntario().getId().equals(solicitanteId);

        if (!isVitima && !isAdvogado) {
            throw new BusinessException("Apenas a vítima ou o advogado participante podem encerrar o caso.");
        }

        if (caso.getStatus() == StatusCaso.ENCERRADO || caso.getStatus() == StatusCaso.ARQUIVADO) {
            throw new BusinessException("Caso já foi encerrado ou arquivado.");
        }

        // RN12: resultado é obrigatório e está validado pelo DTO (@NotBlank)
        caso.setResultado(request.resultado());
        caso.setStatus(StatusCaso.ENCERRADO);
        caso.setTimestampEncerramento(LocalDateTime.now());

        // RN13: remover conteúdo de todas as mensagens
        mensagemRepository.removerConteudoPorCasoId(casoId);

        // Decrementar advogado se houver
        AdvogadoVoluntario advogado = caso.getAdvogadoVoluntario();
        if (advogado != null) {
            advogado.setTotalCasosAtivos(Math.max(0, advogado.getTotalCasosAtivos() - 1));
            advogadoVoluntarioRepository.save(advogado);
        }

        Caso casoSalvo = casoRepository.save(caso);
        return mapearParaResponse(casoSalvo);
    }

    @Transactional(readOnly = true)
    public CasoResponse buscarPorId(UUID casoId, UUID solicitanteId) {
        Caso caso = casoRepository.findById(casoId)
                .orElseThrow(() -> new ResourceNotFoundException("Caso não encontrado com ID: " + casoId));

        Usuario solicitante = usuarioRepository.findById(solicitanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário solicitante não encontrado com ID: " + solicitanteId));

        boolean isVitima = caso.getVitima().getId().equals(solicitanteId);
        boolean isAdvogado = caso.getAdvogadoVoluntario() != null && caso.getAdvogadoVoluntario().getId().equals(solicitanteId);
        boolean isAdmin = solicitante instanceof Administrador;

        if (!isVitima && !isAdvogado && !isAdmin) {
            throw new BusinessException("Acesso negado a este caso.");
        }

        return mapearParaResponse(caso);
    }

    @Transactional(readOnly = true)
    public List<CasoResponse> listarCasosDaVitima(UUID vitimaId) {
        return casoRepository.findAllByVitimaId(vitimaId)
                .stream()
                .map(this::mapearParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CasoResponse> listarCasosAdvogado(UUID advogadoId) {
        return casoRepository.findAllByAdvogadoVoluntarioId(advogadoId)
                .stream()
                .map(this::mapearParaResponse)
                .toList();
    }

    public CasoResponse mapearParaResponse(Caso caso) {
        VitimaResponse vitimaResponse = null;
        if (caso.getVitima() != null) {
            vitimaResponse = new VitimaResponse(
                    caso.getVitima().getId(),
                    caso.getVitima().getNomeAnonimo(),
                    caso.getVitima().getEstadoResidencia(),
                    caso.getVitima().getDataCadastro()
            );
        }

        AdvogadoResponse advogadoResponse = null;
        if (caso.getAdvogadoVoluntario() != null) {
            AdvogadoVoluntario adv = caso.getAdvogadoVoluntario();
            advogadoResponse = new AdvogadoResponse(
                    adv.getId(),
                    adv.getNome(),
                    adv.getNumeroOAB(),
                    adv.getStatusAprovacao(),
                    adv.getEspecialidades(),
                    adv.getDisponibilidade(),
                    adv.getDataAprovacao()
            );
        }

        return new CasoResponse(
                caso.getId(),
                caso.getProtocolo(),
                caso.getTipoViolencia(),
                caso.getStatus(),
                caso.getTimestampAbertura(),
                caso.getTimestampEncerramento(),
                caso.getResultado(),
                vitimaResponse,
                advogadoResponse
        );
    }
}
