package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.response.VitimaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vitimas")
@RequiredArgsConstructor
public class VitimaController {

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<VitimaResponse> obterPerfil(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID da vítima autenticada pelo email de userDetails.
        //       Buscar Vitima pelo UUID via VitimaRepository.
        //       RN02: nunca expor dados de identificação real.
        //       Retornar 200 OK com VitimaResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @DeleteMapping("/conta")
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<Void> excluirConta(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID da vítima autenticada.
        //       RN14: desativar conta (ativo = false), não deletar fisicamente.
        //       Revogar todos os refresh tokens da vítima.
        //       Retornar 204 NO CONTENT.
        throw new UnsupportedOperationException("Não implementado");
    }
}
