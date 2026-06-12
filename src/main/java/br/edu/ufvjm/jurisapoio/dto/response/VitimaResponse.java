package br.edu.ufvjm.jurisapoio.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

// RN02: nunca expor email, nome real ou CPF — apenas nomeAnonimo
public record VitimaResponse(
        UUID id,
        String nomeAnonimo,
        String estadoResidencia,
        LocalDateTime dataCadastro
) {}
