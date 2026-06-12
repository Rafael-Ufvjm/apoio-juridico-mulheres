package br.edu.ufvjm.jurisapoio.dto.response;

import br.edu.ufvjm.jurisapoio.enums.CategoriaDocumento;

import java.util.UUID;

// RN05: nunca retornar nome original — apenas metadados sem identificação do arquivo
public record DocumentoResponse(
        UUID id,
        CategoriaDocumento categoria,
        String tipoMIME,
        Long tamanhoBytes,
        Boolean permissaoAdvogado
) {}
