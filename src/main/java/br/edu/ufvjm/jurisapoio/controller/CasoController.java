package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.request.CasoTriagemRequest;
import br.edu.ufvjm.jurisapoio.dto.request.EncerrarCasoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.CasoResponse;
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

    @PostMapping
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<CasoResponse> abrirCaso(
            @Valid @RequestBody CasoTriagemRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID da vítima autenticada pelo email de userDetails.
        //       RN01: delegar para casoService.abrirCaso(vitimaId, request).
        //       RN07: gerar protocolo único no service.
        //       Retornar 201 CREATED com CasoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasoResponse> buscarPorId(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do solicitante autenticado pelo email.
        //       Delegar para casoService.buscarPorId(id, solicitanteId).
        //       Service verifica se o solicitante tem acesso ao caso (vítima do caso ou advogado atribuído ou ADMIN).
        //       Retornar 200 OK com CasoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @GetMapping("/meus")
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<List<CasoResponse>> listarCasosDaVitima(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID da vítima autenticada.
        //       Delegar para casoService.listarCasosDaVitima(vitimaId).
        //       Retornar 200 OK com lista.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<CasoResponse> encerrarCaso(
            @PathVariable UUID id,
            @Valid @RequestBody EncerrarCasoRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do solicitante autenticado pelo email de userDetails.
        //       RN12: delegar para casoService.encerrarCaso(id, solicitanteId, request).
        //       RN13: MensagemService.removerConteudoPorCasoId é chamado dentro do service.
        //       Retornar 200 OK com CasoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PutMapping("/{id}/advogado/{advogadoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CasoResponse> atribuirAdvogado(
            @PathVariable UUID id,
            @PathVariable UUID advogadoId
    ) {
        // TODO: RN10: delegar para casoService.atribuirAdvogado(id, advogadoId).
        //       Retornar 200 OK com CasoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }
}
