package br.edu.ufvjm.jurisapoio.dto.response;

import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record MensagemResponse(
        UUID id,
        PerfilUsuario remetentePerfil,
        String conteudo,
        LocalDateTime dataEnvio,
        Boolean conteudoRemovido
) {}
