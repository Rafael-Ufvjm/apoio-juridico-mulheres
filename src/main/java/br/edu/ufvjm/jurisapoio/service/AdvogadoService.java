package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.enums.Disponibilidade;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvogadoService {

    private final AdvogadoVoluntarioRepository advogadoVoluntarioRepository;

    @Transactional(readOnly = true)
    public AdvogadoResponse buscarPorId(UUID id) {
        AdvogadoVoluntario advogado = advogadoVoluntarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advogado não encontrado com ID: " + id));
        return mapearParaResponse(advogado);
    }

    @Transactional(readOnly = true)
    public AdvogadoResponse obterPerfil(String email) {
        AdvogadoVoluntario advogado = advogadoVoluntarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Advogado não encontrado com email: " + email));
        return mapearParaResponse(advogado);
    }

    @Transactional
    public AdvogadoResponse atualizarDisponibilidade(String email, Disponibilidade disponibilidade) {
        AdvogadoVoluntario advogado = advogadoVoluntarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Advogado não encontrado com email: " + email));

        if (advogado.getStatusAprovacao() != StatusAprovacao.ATIVO) {
            throw new BusinessException("Apenas advogados com perfil aprovado (ATIVO) podem alterar a disponibilidade.");
        }

        advogado.setDisponibilidade(disponibilidade);
        AdvogadoVoluntario salvo = advogadoVoluntarioRepository.save(advogado);
        return mapearParaResponse(salvo);
    }

    @Transactional(readOnly = true)
    public java.util.List<AdvogadoResponse> listarAtivos() {
        return advogadoVoluntarioRepository.findAllByStatusAprovacao(StatusAprovacao.ATIVO).stream()
                .map(this::mapearParaResponse)
                .toList();
    }

    public AdvogadoResponse mapearParaResponse(AdvogadoVoluntario advogado) {
        return new AdvogadoResponse(
                advogado.getId(),
                advogado.getNome(),
                advogado.getNumeroOAB(),
                advogado.getStatusAprovacao(),
                advogado.getEspecialidades(),
                advogado.getDisponibilidade(),
                advogado.getDataAprovacao()
        );
    }
}
