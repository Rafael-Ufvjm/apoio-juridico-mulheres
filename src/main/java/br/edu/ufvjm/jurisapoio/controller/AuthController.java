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
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/cadastro/vitima")
    public ResponseEntity<AuthResponse> cadastrarVitima(@Valid @RequestBody CadastroVitimaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.cadastrarVitima(request));
    }

    @PostMapping("/cadastro/advogado")
    public ResponseEntity<AuthResponse> cadastrarAdvogado(@Valid @RequestBody AdvogadoCadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.cadastrarAdvogado(request));
    }

    @PostMapping("/token/renovar")
    public ResponseEntity<AuthResponse> renovarToken(@RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader;
        return ResponseEntity.ok(authService.renovarToken(refreshToken));
    }
}
