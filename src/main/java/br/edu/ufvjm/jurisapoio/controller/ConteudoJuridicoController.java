package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.request.ConteudoJuridicoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.ConteudoJuridicoResponse;
import br.edu.ufvjm.jurisapoio.enums.CategoriaConteudo;
import br.edu.ufvjm.jurisapoio.service.ConteudoJuridicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/conteudos")
@RequiredArgsConstructor
public class ConteudoJuridicoController {

    private final ConteudoJuridicoService conteudoJuridicoService;

    @GetMapping
    public ResponseEntity<List<ConteudoJuridicoResponse>> listar(
            @RequestParam(required = false) CategoriaConteudo categoria
    ) {
        List<ConteudoJuridicoResponse> resultado = categoria != null
                ? conteudoJuridicoService.listarPorCategoria(categoria)
                : conteudoJuridicoService.listarPublicados();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoJuridicoResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(conteudoJuridicoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConteudoJuridicoResponse> criar(
            @Valid @RequestBody ConteudoJuridicoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conteudoJuridicoService.criar(request));
    }

    @PatchMapping("/{id}/publicar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConteudoJuridicoResponse> publicar(@PathVariable UUID id) {
        return ResponseEntity.ok(conteudoJuridicoService.publicar(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConteudoJuridicoResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ConteudoJuridicoRequest request
    ) {
        return ResponseEntity.ok(conteudoJuridicoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        conteudoJuridicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}