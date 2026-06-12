package br.edu.ufvjm.jurisapoio.dto.response;

import br.edu.ufvjm.jurisapoio.enums.Disponibilidade;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdvogadoResponse(
        UUID id,
        String nome,
        String numeroOAB,
        StatusAprovacao statusAprovacao,
        String especialidades,
        Disponibilidade disponibilidade,
        LocalDateTime dataAprovacao
) {}
