package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.request.AprovarAdvogadoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/advogados/pendentes")
    public ResponseEntity<List<AdvogadoResponse>> listarAdvogadosPendentes() {
        List<AdvogadoResponse> pendentes = adminService.listarAdvogadosPendentes();
        return ResponseEntity.ok(pendentes);
    }

    @PutMapping("/advogados/{id}/aprovar")
    public ResponseEntity<AdvogadoResponse> processarAprovacao(
            @PathVariable UUID id,
            @Valid @RequestBody AprovarAdvogadoRequest request
    ) {
        AdvogadoResponse response = adminService.processarAprovacao(id, request);
        return ResponseEntity.ok(response);
    }
}
