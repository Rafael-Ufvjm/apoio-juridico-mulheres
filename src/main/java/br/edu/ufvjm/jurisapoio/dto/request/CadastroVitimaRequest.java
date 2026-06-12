package br.edu.ufvjm.jurisapoio.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// RN02: apenas nomeAnonimo é solicitado — nome real e CPF nunca coletados
public record CadastroVitimaRequest(
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String senha,

        @NotBlank(message = "Nome anônimo é obrigatório")
        @Size(max = 100, message = "Nome anônimo deve ter no máximo 100 caracteres")
        String nomeAnonimo,

        String estadoResidencia,

        @NotNull(message = "É necessário aceitar os termos de uso")
        Boolean aceitouTermos
) {}
