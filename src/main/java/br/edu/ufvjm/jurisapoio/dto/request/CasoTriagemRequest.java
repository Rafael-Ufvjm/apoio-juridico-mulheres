package br.edu.ufvjm.jurisapoio.dto.request;

import br.edu.ufvjm.jurisapoio.enums.TipoViolencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// RN01: vítima só pode ter 1 caso ativo — verificado em CasoService
public record CasoTriagemRequest(
        @NotNull(message = "Tipo de violência é obrigatório")
        TipoViolencia tipoViolencia,

        @NotBlank(message = "Descrição da situação é obrigatória")
        @Size(min = 20, message = "Descreva a situação com pelo menos 20 caracteres")
        String descricao
) {}
