package br.edu.ufvjm.jurisapoio.dto.request;

import br.edu.ufvjm.jurisapoio.enums.CategoriaConteudo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConteudoJuridicoRequest(
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Corpo do conteúdo é obrigatório")
        String corpo,

        @NotNull(message = "Categoria é obrigatória")
        CategoriaConteudo categoria,

        String tags,
        String nivelLinguagem,
        String revisadoPor
) {}
