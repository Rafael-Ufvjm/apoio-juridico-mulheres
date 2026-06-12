package br.edu.ufvjm.jurisapoio.dto.response;

import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        PerfilUsuario perfil,
        String identificador
) {}
