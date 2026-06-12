package br.edu.ufvjm.jurisapoio.dto.response;

import br.edu.ufvjm.jurisapoio.enums.CategoriaConteudo;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConteudoJuridicoResponse(
        UUID id,
        String titulo,
        String corpo,
        CategoriaConteudo categoria,
        String tags,
        String nivelLinguagem,
        LocalDateTime dataPublicacao,
        String revisadoPor
) {}
