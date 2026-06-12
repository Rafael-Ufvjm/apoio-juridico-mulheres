package br.edu.ufvjm.jurisapoio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MensagemRequest(
        @NotBlank(message = "Conteúdo da mensagem é obrigatório")
        @Size(max = 5000, message = "Mensagem deve ter no máximo 5000 caracteres")
        String conteudo
) {}
