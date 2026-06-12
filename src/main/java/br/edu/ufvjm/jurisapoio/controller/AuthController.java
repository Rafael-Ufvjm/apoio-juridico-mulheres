package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.request.AdvogadoCadastroRequest;
import br.edu.ufvjm.jurisapoio.dto.request.CadastroVitimaRequest;
import br.edu.ufvjm.jurisapoio.dto.request.LoginRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AuthResponse;
import br.edu.ufvjm.jurisapoio.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // TODO: Delegar para authService.login(request).
        //       Retornar 200 OK com AuthResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PostMapping("/cadastro/vitima")
    public ResponseEntity<AuthResponse> cadastrarVitima(@Valid @RequestBody CadastroVitimaRequest request) {
        // TODO: Delegar para authService.cadastrarVitima(request).
        //       RN02: controller nunca deve solicitar nem repassar nome real ou CPF.
        //       Retornar 201 CREATED com AuthResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PostMapping("/cadastro/advogado")
    public ResponseEntity<AuthResponse> cadastrarAdvogado(@Valid @RequestBody AdvogadoCadastroRequest request) {
        // TODO: Delegar para authService.cadastrarAdvogado(request).
        //       RN16: OAB único verificado no service.
        //       Retornar 201 CREATED com AuthResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PostMapping("/token/renovar")
    public ResponseEntity<AuthResponse> renovarToken(@RequestHeader("Authorization") String authorizationHeader) {
        // TODO: Extrair o refresh token do header Authorization (formato "Bearer <token>").
        //       Delegar para authService.renovarToken(refreshToken).
        //       RN20: refresh token inválido, expirado ou revogado deve retornar 401.
        //       Retornar 200 OK com novo AuthResponse contendo novo accessToken e refreshToken.
        throw new UnsupportedOperationException("Não implementado");
    }
}
