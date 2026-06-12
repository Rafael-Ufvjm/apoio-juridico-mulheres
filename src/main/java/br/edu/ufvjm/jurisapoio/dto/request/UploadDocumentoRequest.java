package br.edu.ufvjm.jurisapoio.dto.request;

import br.edu.ufvjm.jurisapoio.enums.CategoriaDocumento;
import jakarta.validation.constraints.NotNull;

// RN05: nomeOriginal usado apenas em memória para gerar criptografia — nunca persistido em texto plano
public record UploadDocumentoRequest(
        @NotNull(message = "Categoria do documento é obrigatória")
        CategoriaDocumento categoria
) {}
