package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
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
        // TODO: Buscar AdvogadoVoluntario pelo id ou lançar ResourceNotFoundException.
        //       Mapear para AdvogadoResponse e retornar.
        throw new UnsupportedOperationException("Não implementado");
    }

    public AdvogadoResponse mapearParaResponse(AdvogadoVoluntario advogado) {
        // TODO: Converter entidade AdvogadoVoluntario em AdvogadoResponse.
        //       Campos: id, nome, numeroOAB, statusAprovacao, especialidades, disponibilidade, dataAprovacao.
        throw new UnsupportedOperationException("Não implementado");
    }
}
