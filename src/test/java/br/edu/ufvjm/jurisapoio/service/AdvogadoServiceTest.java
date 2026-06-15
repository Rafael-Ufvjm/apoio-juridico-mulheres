package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.enums.Disponibilidade;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvogadoServiceTest {

    @Mock
    private AdvogadoVoluntarioRepository advogadoVoluntarioRepository;

    @InjectMocks
    private AdvogadoService advogadoService;

    private AdvogadoVoluntario advogado;
    private UUID id;
    private String email;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        email = "advogado@teste.com";
        advogado = AdvogadoVoluntario.builder()
                .id(id)
                .nome("Dr. Roberto")
                .email(email)
                .numeroOAB("123456/MG")
                .statusAprovacao(StatusAprovacao.ATIVO)
                .especialidades("Violência Doméstica, Família")
                .disponibilidade(Disponibilidade.OFFLINE)
                .dataAprovacao(LocalDateTime.now())
                .build();
    }

    @Test
    void buscarPorId_DeveRetornarResponse_QuandoAdvogadoExiste() {
        when(advogadoVoluntarioRepository.findById(id)).thenReturn(Optional.of(advogado));

        AdvogadoResponse response = advogadoService.buscarPorId(id);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.nome()).isEqualTo("Dr. Roberto");
        assertThat(response.numeroOAB()).isEqualTo("123456/MG");
        verify(advogadoVoluntarioRepository, times(1)).findById(id);
    }

    @Test
    void buscarPorId_DeveLancarResourceNotFoundException_QuandoAdvogadoNaoExiste() {
        when(advogadoVoluntarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> advogadoService.buscarPorId(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Advogado não encontrado com ID: " + id);
    }

    @Test
    void obterPerfil_DeveRetornarResponse_QuandoAdvogadoExiste() {
        when(advogadoVoluntarioRepository.findByEmail(email)).thenReturn(Optional.of(advogado));

        AdvogadoResponse response = advogadoService.obterPerfil(email);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.nome()).isEqualTo("Dr. Roberto");
        verify(advogadoVoluntarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void obterPerfil_DeveLancarResourceNotFoundException_QuandoAdvogadoNaoExiste() {
        when(advogadoVoluntarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> advogadoService.obterPerfil(email))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Advogado não encontrado com email: " + email);
    }

    @Test
    void atualizarDisponibilidade_DeveAlterarESalvar_QuandoAdvogadoAtivo() {
        when(advogadoVoluntarioRepository.findByEmail(email)).thenReturn(Optional.of(advogado));
        when(advogadoVoluntarioRepository.save(any(AdvogadoVoluntario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AdvogadoResponse response = advogadoService.atualizarDisponibilidade(email, Disponibilidade.ONLINE);

        assertThat(response).isNotNull();
        assertThat(response.disponibilidade()).isEqualTo(Disponibilidade.ONLINE);
        verify(advogadoVoluntarioRepository, times(1)).findByEmail(email);
        verify(advogadoVoluntarioRepository, times(1)).save(any(AdvogadoVoluntario.class));
    }

    @Test
    void atualizarDisponibilidade_DeveLancarBusinessException_QuandoAdvogadoNaoAtivo() {
        advogado.setStatusAprovacao(StatusAprovacao.PENDENTE);
        when(advogadoVoluntarioRepository.findByEmail(email)).thenReturn(Optional.of(advogado));

        assertThatThrownBy(() -> advogadoService.atualizarDisponibilidade(email, Disponibilidade.ONLINE))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Apenas advogados com perfil aprovado (ATIVO) podem alterar a disponibilidade.");

        verify(advogadoVoluntarioRepository, never()).save(any());
    }

    @Test
    void atualizarDisponibilidade_DeveLancarResourceNotFoundException_QuandoAdvogadoNaoExiste() {
        when(advogadoVoluntarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> advogadoService.atualizarDisponibilidade(email, Disponibilidade.ONLINE))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Advogado não encontrado com email: " + email);

        verify(advogadoVoluntarioRepository, never()).save(any());
    }
}
