package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.response.VitimaResponse;
import br.edu.ufvjm.jurisapoio.entity.Vitima;
import br.edu.ufvjm.jurisapoio.repository.VitimaRepository;
import br.edu.ufvjm.jurisapoio.repository.RefreshTokenRepository;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/vitimas")
@RequiredArgsConstructor
public class VitimaController {

    private final VitimaRepository vitimaRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<VitimaResponse> obterPerfil(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Vitima vitima = vitimaRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Vítima não encontrada com o email fornecido."));
        VitimaResponse response = new VitimaResponse(
                vitima.getId(),
                vitima.getNomeAnonimo(),
                vitima.getEstadoResidencia(),
                vitima.getDataCadastro()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/conta")
    @PreAuthorize("hasRole('VITIMA')")
    @Transactional
    public ResponseEntity<Void> excluirConta(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Vitima vitima = vitimaRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Vítima não encontrada com o email fornecido."));
        vitima.setAtivo(false);
        vitimaRepository.save(vitima);
        refreshTokenRepository.revogarTodosPorUsuario(vitima.getId());
        return ResponseEntity.noContent().build();
    }
}
