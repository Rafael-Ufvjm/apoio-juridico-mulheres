package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.AprovarAdvogadoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
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
        // TODO: Buscar todos com statusAprovacao = PENDENTE via advogadoVoluntarioRepository.findAllByStatusAprovacao().
        //       Mapear cada entidade para AdvogadoResponse e retornar lista.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public AdvogadoResponse processarAprovacao(UUID advogadoId, AprovarAdvogadoRequest request) {
        // TODO: Buscar AdvogadoVoluntario pelo advogadoId ou lançar ResourceNotFoundException.
        //       Verificar que statusAprovacao == PENDENTE, caso contrário BusinessException("Advogado já foi processado.").
        //       RN18: se request.aprovado() == false, verificar que request.justificativa() não está em branco.
        //       Se justificativa ausente na recusa, lançar BusinessException("Justificativa obrigatória para recusa.").
        //       Se aprovado: setar statusAprovacao = ATIVO e dataAprovacao = now().
        //       Se recusado: setar statusAprovacao = RECUSADO e justificativaRejeicao = request.justificativa().
        //       Persistir e retornar AdvogadoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }
}
