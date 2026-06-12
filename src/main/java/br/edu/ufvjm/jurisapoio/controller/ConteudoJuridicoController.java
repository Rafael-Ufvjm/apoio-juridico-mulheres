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
        // TODO: Retornar apenas conteúdos publicados (publicado = true).
        //       Filtrar por categoria se fornecida.
        //       Delegar para conteudoJuridicoService.listar(categoria).
        //       Endpoint público — não requer autenticação.
        //       Retornar 200 OK com lista.
        throw new UnsupportedOperationException("Não implementado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoJuridicoResponse> buscarPorId(@PathVariable UUID id) {
        // TODO: Delegar para conteudoJuridicoService.buscarPorId(id).
        //       Retornar apenas se publicado = true (usuário não autenticado não pode ver rascunhos).
        //       Retornar 200 OK com ConteudoJuridicoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConteudoJuridicoResponse> criar(
            @Valid @RequestBody ConteudoJuridicoRequest request
    ) {
        // TODO: Delegar para conteudoJuridicoService.criar(request).
        //       Retornar 201 CREATED com ConteudoJuridicoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConteudoJuridicoResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ConteudoJuridicoRequest request
    ) {
        // TODO: Delegar para conteudoJuridicoService.atualizar(id, request).
        //       Retornar 200 OK com ConteudoJuridicoResponse atualizado.
        throw new UnsupportedOperationException("Não implementado");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        // TODO: Delegar para conteudoJuridicoService.deletar(id).
        //       Retornar 204 NO CONTENT.
        throw new UnsupportedOperationException("Não implementado");
    }
}
