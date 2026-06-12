package br.edu.ufvjm.jurisapoio.dto.response;

import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import br.edu.ufvjm.jurisapoio.enums.TipoViolencia;

import java.time.LocalDateTime;
import java.util.UUID;

public record CasoResponse(
        UUID id,
        String protocolo,
        TipoViolencia tipoViolencia,
        StatusCaso status,
        LocalDateTime timestampAbertura,
        LocalDateTime timestampEncerramento,
        String resultado,
        VitimaResponse vitima,
        AdvogadoResponse advogado
) {}
