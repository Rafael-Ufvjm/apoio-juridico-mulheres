package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.request.CasoTriagemRequest;
import br.edu.ufvjm.jurisapoio.dto.request.EncerrarCasoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.CasoResponse;
import br.edu.ufvjm.jurisapoio.entity.Usuario;
import br.edu.ufvjm.jurisapoio.entity.Vitima;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.UsuarioRepository;
import br.edu.ufvjm.jurisapoio.repository.VitimaRepository;
import br.edu.ufvjm.jurisapoio.repository.AdvogadoVoluntarioRepository;
import br.edu.ufvjm.jurisapoio.service.CasoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/casos")
@RequiredArgsConstructor
public class CasoController {

    private final CasoService casoService;
    private final VitimaRepository vitimaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AdvogadoVoluntarioRepository advogadoVoluntarioRepository;

    @PostMapping
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<CasoResponse> abrirCaso(
            @Valid @RequestBody CasoTriagemRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Vitima vitima = vitimaRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Vítima não encontrada com o email fornecido."));
        CasoResponse response = casoService.abrirCaso(vitima.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasoResponse> buscarPorId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Usuario solicitante = usuarioRepository.findByEmailAndAtivoTrue(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário solicitante não encontrado."));
        CasoResponse response = casoService.buscarPorId(id, solicitante.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meus")
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<List<CasoResponse>> listarCasosDaVitima(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Vitima vitima = vitimaRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Vítima não encontrada."));
        List<CasoResponse> response = casoService.listarCasosDaVitima(vitima.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<CasoResponse> encerrarCaso(
            @PathVariable UUID id,
            @Valid @RequestBody EncerrarCasoRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Usuario solicitante = usuarioRepository.findByEmailAndAtivoTrue(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário solicitante não encontrado."));
        CasoResponse response = casoService.encerrarCaso(id, solicitante.getId(), request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/advogado/{advogadoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CasoResponse> atribuirAdvogado(
            @PathVariable UUID id,
            @PathVariable UUID advogadoId
    ) {
        CasoResponse response = casoService.atribuirAdvogado(id, advogadoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pendentes")
    @PreAuthorize("hasAnyRole('ADVOGADO_VOLUNTARIO', 'ADMIN')")
    public ResponseEntity<List<CasoResponse>> listarCasosPendentes() {
        List<CasoResponse> response = casoService.listarCasosPendentes();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/aceitar")
    @PreAuthorize("hasRole('ADVOGADO_VOLUNTARIO')")
    public ResponseEntity<CasoResponse> aceitarCaso(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        AdvogadoVoluntario advogado = advogadoVoluntarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Advogada não encontrada com o email logado."));
        CasoResponse response = casoService.atribuirAdvogado(id, advogado.getId());
        return ResponseEntity.ok(response);
    }
}
