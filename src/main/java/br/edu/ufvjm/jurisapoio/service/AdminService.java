package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.AprovarAdvogadoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdvogadoVoluntarioRepository advogadoVoluntarioRepository;

    @Transactional(readOnly = true)
    public List<AdvogadoResponse> listarAdvogadosPendentes() {
        return advogadoVoluntarioRepository.findAllByStatusAprovacao(StatusAprovacao.PENDENTE)
                .stream()
                .map(advogado -> new AdvogadoResponse(
                        advogado.getId(),
                        advogado.getNome(),
                        advogado.getNumeroOAB(),
                        advogado.getStatusAprovacao(),
                        advogado.getEspecialidades(),
                        advogado.getDisponibilidade(),
                        advogado.getDataAprovacao()
                ))
                .toList();
    }

    @Transactional
    public AdvogadoResponse processarAprovacao(UUID advogadoId, AprovarAdvogadoRequest request) {
        AdvogadoVoluntario advogado = advogadoVoluntarioRepository.findById(advogadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Advogado não encontrado com ID: " + advogadoId));

        if (advogado.getStatusAprovacao() != StatusAprovacao.PENDENTE) {
            throw new BusinessException("Advogado já foi processado.");
        }

        if (Boolean.FALSE.equals(request.aprovado())) {
            if (request.justificativa() == null || request.justificativa().trim().isEmpty()) {
                throw new BusinessException("Justificativa obrigatória para recusa.");
            }
            advogado.setStatusAprovacao(StatusAprovacao.RECUSADO);
            advogado.setJustificativaRejeicao(request.justificativa().trim());
        } else {
            advogado.setStatusAprovacao(StatusAprovacao.ATIVO);
            advogado.setDataAprovacao(LocalDateTime.now());
        }

        AdvogadoVoluntario salvo = advogadoVoluntarioRepository.save(advogado);
        return new AdvogadoResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getNumeroOAB(),
                salvo.getStatusAprovacao(),
                salvo.getEspecialidades(),
                salvo.getDisponibilidade(),
                salvo.getDataAprovacao()
        );
    }
}
