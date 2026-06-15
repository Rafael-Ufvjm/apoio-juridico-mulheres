package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.response.AdvogadoResponse;
import br.edu.ufvjm.jurisapoio.dto.response.CasoResponse;
import br.edu.ufvjm.jurisapoio.enums.Disponibilidade;
import br.edu.ufvjm.jurisapoio.service.AdvogadoService;
import br.edu.ufvjm.jurisapoio.service.CasoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advogados")
@RequiredArgsConstructor
public class AdvogadoController {

    private final AdvogadoService advogadoService;
    private final CasoService casoService;

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('ADVOGADO_VOLUNTARIO')")
    public ResponseEntity<AdvogadoResponse> obterPerfil(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        AdvogadoResponse response = advogadoService.obterPerfil(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/disponibilidade")
    @PreAuthorize("hasRole('ADVOGADO_VOLUNTARIO')")
    public ResponseEntity<AdvogadoResponse> atualizarDisponibilidade(
            @RequestParam Disponibilidade disponibilidade,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        AdvogadoResponse response = advogadoService.atualizarDisponibilidade(userDetails.getUsername(), disponibilidade);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/casos")
    @PreAuthorize("hasRole('ADVOGADO_VOLUNTARIO')")
    public ResponseEntity<List<CasoResponse>> listarCasosDoAdvogado(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        AdvogadoResponse response = advogadoService.obterPerfil(userDetails.getUsername());
        List<CasoResponse> casos = casoService.listarCasosAdvogado(response.id());
        return ResponseEntity.ok(casos);
    }
}
