package br.edu.ufvjm.jurisapoio.dto.response;

import java.time.LocalDateTime;

public record ErroResponse(
        String mensagem,
        LocalDateTime timestamp,
        int status
) {}
