package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.AprovarAdvogadoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdvogadoVoluntarioRepository advogadoVoluntarioRepository;

    @InjectMocks
    private AdminService adminService;

    private AdvogadoVoluntario advogado;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        advogado = AdvogadoVoluntario.builder()
                .id(id)
                .nome("Dr. Roberto")
                .email("advogado@teste.com")
                .numeroOAB("123456/MG")
                .statusAprovacao(StatusAprovacao.PENDENTE)
                .especialidades("Família")
                .build();
    }

    @Test
    void listarAdvogadosPendentes_DeveRetornarListaDeResponses() {
        when(advogadoVoluntarioRepository.findAllByStatusAprovacao(StatusAprovacao.PENDENTE))
                .thenReturn(List.of(advogado));

        List<AdvogadoResponse> response = adminService.listarAdvogadosPendentes();

        assertThat(response).hasSize(1);
        assertThat(response.get(0).id()).isEqualTo(id);
        assertThat(response.get(0).statusAprovacao()).isEqualTo(StatusAprovacao.PENDENTE);
        verify(advogadoVoluntarioRepository, times(1)).findAllByStatusAprovacao(StatusAprovacao.PENDENTE);
    }

    @Test
    void processarAprovacao_DeveAprovarAdvogado_QuandoRequestAprovar() {
        AprovarAdvogadoRequest request = new AprovarAdvogadoRequest(true, null);
        when(advogadoVoluntarioRepository.findById(id)).thenReturn(Optional.of(advogado));
        when(advogadoVoluntarioRepository.save(any(AdvogadoVoluntario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AdvogadoResponse response = adminService.processarAprovacao(id, request);

        assertThat(response).isNotNull();
        assertThat(response.statusAprovacao()).isEqualTo(StatusAprovacao.ATIVO);
        assertThat(response.dataAprovacao()).isNotNull();
        verify(advogadoVoluntarioRepository, times(1)).save(any(AdvogadoVoluntario.class));
    }

    @Test
    void processarAprovacao_DeveRecusarAdvogado_QuandoRequestRecusarComJustificativa() {
        AprovarAdvogadoRequest request = new AprovarAdvogadoRequest(false, "OAB suspensa");
        when(advogadoVoluntarioRepository.findById(id)).thenReturn(Optional.of(advogado));
        when(advogadoVoluntarioRepository.save(any(AdvogadoVoluntario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AdvogadoResponse response = adminService.processarAprovacao(id, request);

        assertThat(response).isNotNull();
        assertThat(response.statusAprovacao()).isEqualTo(StatusAprovacao.RECUSADO);
        assertThat(advogado.getJustificativaRejeicao()).isEqualTo("OAB suspensa");
        verify(advogadoVoluntarioRepository, times(1)).save(any(AdvogadoVoluntario.class));
    }

    @Test
    void processarAprovacao_DeveLancarBusinessException_QuandoRecusarSemJustificativa() {
        AprovarAdvogadoRequest requestSemJustificativa = new AprovarAdvogadoRequest(false, "   ");
        when(advogadoVoluntarioRepository.findById(id)).thenReturn(Optional.of(advogado));

        assertThatThrownBy(() -> adminService.processarAprovacao(id, requestSemJustificativa))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Justificativa obrigatória para recusa.");

        verify(advogadoVoluntarioRepository, never()).save(any());
    }

    @Test
    void processarAprovacao_DeveLancarBusinessException_QuandoAdvogadoJaProcessado() {
        advogado.setStatusAprovacao(StatusAprovacao.ATIVO);
        AprovarAdvogadoRequest request = new AprovarAdvogadoRequest(true, null);
        when(advogadoVoluntarioRepository.findById(id)).thenReturn(Optional.of(advogado));

        assertThatThrownBy(() -> adminService.processarAprovacao(id, request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Advogado já foi processado.");

        verify(advogadoVoluntarioRepository, never()).save(any());
    }

    @Test
    void processarAprovacao_DeveLancarResourceNotFoundException_QuandoAdvogadoNaoExiste() {
        AprovarAdvogadoRequest request = new AprovarAdvogadoRequest(true, null);
        when(advogadoVoluntarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.processarAprovacao(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Advogado não encontrado com ID: " + id);

        verify(advogadoVoluntarioRepository, never()).save(any());
    }
}
