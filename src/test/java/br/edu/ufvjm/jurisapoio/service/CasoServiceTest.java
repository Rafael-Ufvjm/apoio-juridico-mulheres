package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.CasoTriagemRequest;
import br.edu.ufvjm.jurisapoio.dto.request.EncerrarCasoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.CasoResponse;
import br.edu.ufvjm.jurisapoio.entity.Administrador;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.entity.Caso;
import br.edu.ufvjm.jurisapoio.entity.Mensagem;
import br.edu.ufvjm.jurisapoio.entity.Vitima;
import br.edu.ufvjm.jurisapoio.entity.Usuario;
import br.edu.ufvjm.jurisapoio.enums.Disponibilidade;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import br.edu.ufvjm.jurisapoio.enums.TipoViolencia;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import br.edu.ufvjm.jurisapoio.repository.CasoRepository;
import br.edu.ufvjm.jurisapoio.repository.MensagemRepository;
import br.edu.ufvjm.jurisapoio.repository.UsuarioRepository;
import br.edu.ufvjm.jurisapoio.repository.VitimaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CasoServiceTest {

    @Mock
    private CasoRepository casoRepository;
    @Mock
    private VitimaRepository vitimaRepository;
    @Mock
    private AdvogadoVoluntarioRepository advogadoVoluntarioRepository;
    @Mock
    private MensagemRepository mensagemRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CasoService casoService;

    private UUID vitimaId;
    private UUID advogadoId;
    private UUID casoId;
    private Vitima vitima;
    private AdvogadoVoluntario advogado;
    private Caso caso;

    @BeforeEach
    void setUp() {
        vitimaId = UUID.randomUUID();
        advogadoId = UUID.randomUUID();
        casoId = UUID.randomUUID();

        vitima = Vitima.builder()
                .id(vitimaId)
                .nomeAnonimo("Maria Anonima")
                .estadoResidencia("MG")
                .aceitouTermos(true)
                .dataCadastro(LocalDateTime.now())
                .build();

        advogado = AdvogadoVoluntario.builder()
                .id(advogadoId)
                .nome("Dr. Roberto")
                .numeroOAB("123456/MG")
                .statusAprovacao(StatusAprovacao.ATIVO)
                .totalCasosAtivos(0)
                .disponibilidade(Disponibilidade.ONLINE)
                .build();

        caso = Caso.builder()
                .id(casoId)
                .protocolo("JA-2026-ABCDEF12")
                .tipoViolencia(TipoViolencia.FISICA)
                .status(StatusCaso.AGUARDANDO)
                .vitima(vitima)
                .timestampAbertura(LocalDateTime.now())
                .chaveCriptografiaChat("dGVzdGVrZXkyNTZiaXRzZ2VuZXJhdGVkMTIzNDU2Nzg=")
                .build();
    }

    @Test
    void abrirCaso_DeveSalvarCasoEMensagem_QuandoDadosValidos() {
        CasoTriagemRequest request = new CasoTriagemRequest(TipoViolencia.FISICA, "Descrição detalhada com mais de vinte caracteres.");
        when(casoRepository.existsByVitimaIdAndStatusIn(eq(vitimaId), anyList())).thenReturn(false);
        when(vitimaRepository.findById(vitimaId)).thenReturn(Optional.of(vitima));
        when(casoRepository.save(any(Caso.class))).thenAnswer(inv -> inv.getArgument(0));

        CasoResponse response = casoService.abrirCaso(vitimaId, request);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(StatusCaso.AGUARDANDO);
        assertThat(response.protocolo()).startsWith("JA-");
        verify(casoRepository, times(1)).save(any(Caso.class));
        verify(mensagemRepository, times(1)).save(any(Mensagem.class));
    }

    @Test
    void abrirCaso_DeveLancarBusinessException_QuandoVitimaJaTemCasoAtivo() {
        CasoTriagemRequest request = new CasoTriagemRequest(TipoViolencia.FISICA, "Descrição detalhada com mais de vinte caracteres.");
        when(casoRepository.existsByVitimaIdAndStatusIn(eq(vitimaId), anyList())).thenReturn(true);

        assertThatThrownBy(() -> casoService.abrirCaso(vitimaId, request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Você já possui um caso ativo. Encerre-o antes de abrir um novo.");

        verify(casoRepository, never()).save(any());
    }

    @Test
    void atribuirAdvogado_DeveVincularAdvogadoMudarStatusEIncrementarCasos() {
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));
        when(advogadoVoluntarioRepository.findById(advogadoId)).thenReturn(Optional.of(advogado));
        when(casoRepository.countByAdvogadoVoluntarioIdAndStatusIn(eq(advogadoId), anyList())).thenReturn(0L);
        when(casoRepository.save(any(Caso.class))).thenAnswer(inv -> inv.getArgument(0));

        CasoResponse response = casoService.atribuirAdvogado(casoId, advogadoId);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(StatusCaso.EM_ATENDIMENTO);
        assertThat(advogado.getTotalCasosAtivos()).isEqualTo(1);
        verify(advogadoVoluntarioRepository, times(1)).save(advogado);
        verify(casoRepository, times(1)).save(any(Caso.class));
    }

    @Test
    void atribuirAdvogado_DeveLancarBusinessException_QuandoCasoNaoEstiverAguardando() {
        caso.setStatus(StatusCaso.EM_ATENDIMENTO);
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));

        assertThatThrownBy(() -> casoService.atribuirAdvogado(casoId, advogadoId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Este caso não está aguardando atendimento.");
    }

    @Test
    void atribuirAdvogado_DeveLancarBusinessException_QuandoAdvogadoInativo() {
        advogado.setStatusAprovacao(StatusAprovacao.PENDENTE);
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));
        when(advogadoVoluntarioRepository.findById(advogadoId)).thenReturn(Optional.of(advogado));

        assertThatThrownBy(() -> casoService.atribuirAdvogado(casoId, advogadoId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Apenas advogados com perfil aprovado (ATIVO) podem receber casos.");
    }

    @Test
    void atribuirAdvogado_DeveLancarBusinessException_QuandoLimiteDeCasosExcedido() {
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));
        when(advogadoVoluntarioRepository.findById(advogadoId)).thenReturn(Optional.of(advogado));
        when(casoRepository.countByAdvogadoVoluntarioIdAndStatusIn(eq(advogadoId), anyList())).thenReturn(5L);

        assertThatThrownBy(() -> casoService.atribuirAdvogado(casoId, advogadoId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Advogado já possui 5 casos ativos.");
    }

    @Test
    void encerrarCaso_DeveMudarStatusLimparMensagensEDecrementarCasos_QuandoSolicitadoPorParticipante() {
        caso.setStatus(StatusCaso.EM_ATENDIMENTO);
        caso.setAdvogadoVoluntario(advogado);
        advogado.setTotalCasosAtivos(1);
        EncerrarCasoRequest request = new EncerrarCasoRequest("Caso solucionado com conciliação.");

        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));
        when(casoRepository.save(any(Caso.class))).thenAnswer(inv -> inv.getArgument(0));

        CasoResponse response = casoService.encerrarCaso(casoId, vitimaId, request);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(StatusCaso.ENCERRADO);
        assertThat(response.resultado()).isEqualTo("Caso solucionado com conciliação.");
        assertThat(advogado.getTotalCasosAtivos()).isEqualTo(0);
        verify(mensagemRepository, times(1)).removerConteudoPorCasoId(casoId);
        verify(advogadoVoluntarioRepository, times(1)).save(advogado);
        verify(casoRepository, times(1)).save(any(Caso.class));
    }

    @Test
    void encerrarCaso_DeveLancarBusinessException_QuandoSolicitanteNaoForParticipante() {
        UUID estranhoId = UUID.randomUUID();
        EncerrarCasoRequest request = new EncerrarCasoRequest("Resultado");
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));

        assertThatThrownBy(() -> casoService.encerrarCaso(casoId, estranhoId, request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Apenas a vítima ou o advogado participante podem encerrar o caso.");
    }

    @Test
    void buscarPorId_DeveRetornarCaso_QuandoSolicitanteForVitimaDoCaso() {
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));
        when(usuarioRepository.findById(vitimaId)).thenReturn(Optional.of(vitima));

        CasoResponse response = casoService.buscarPorId(casoId, vitimaId);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(casoId);
    }

    @Test
    void buscarPorId_DeveRetornarCaso_QuandoSolicitanteForAdmin() {
        Administrador admin = Administrador.builder().id(UUID.randomUUID()).email("admin@teste.com").build();
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));
        when(usuarioRepository.findById(admin.getId())).thenReturn(Optional.of(admin));

        CasoResponse response = casoService.buscarPorId(casoId, admin.getId());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(casoId);
    }

    @Test
    void buscarPorId_DeveLancarBusinessException_QuandoSolicitanteNaoTiverAcesso() {
        Usuario estranho = Vitima.builder().id(UUID.randomUUID()).email("estranho@teste.com").build();
        when(casoRepository.findById(casoId)).thenReturn(Optional.of(caso));
        when(usuarioRepository.findById(estranho.getId())).thenReturn(Optional.of(estranho));

        assertThatThrownBy(() -> casoService.buscarPorId(casoId, estranho.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Acesso negado a este caso.");
    }
}
