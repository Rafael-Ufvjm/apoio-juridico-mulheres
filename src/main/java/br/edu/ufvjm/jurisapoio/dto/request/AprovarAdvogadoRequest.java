package br.edu.ufvjm.jurisapoio.dto.request;

import jakarta.validation.constraints.NotNull;

// RN18: recusa exige justificativa — validado em AdminService
public record AprovarAdvogadoRequest(
        @NotNull(message = "Campo aprovado é obrigatório")
        Boolean aprovado,

        String justificativa
) {}
