package br.edu.ufvjm.jurisapoio.dto.request;

import jakarta.validation.constraints.NotBlank;

// RN12: resultado obrigatório para encerrar o caso
public record EncerrarCasoRequest(
        @NotBlank(message = "Resultado é obrigatório para encerrar o caso")
        String resultado
) {}
