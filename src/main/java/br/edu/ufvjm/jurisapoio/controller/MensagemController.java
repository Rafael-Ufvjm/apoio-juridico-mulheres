package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.request.MensagemRequest;
import br.edu.ufvjm.jurisapoio.dto.response.MensagemResponse;
import br.edu.ufvjm.jurisapoio.service.MensagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/casos/{casoId}/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<MensagemResponse> enviarMensagem(
            @PathVariable UUID casoId,
            @Valid @RequestBody MensagemRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do remetente autenticado pelo email.
        //       Delegar para mensagemService.enviarMensagem(casoId, remetenteId, request).
        //       RN11: verificação de caso encerrado feita no service.
        //       Conteúdo criptografado no service com AES-256-GCM via CriptografiaUtil.
        //       Retornar 201 CREATED com MensagemResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @GetMapping
    public ResponseEntity<List<MensagemResponse>> listarMensagens(
            @PathVariable UUID casoId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do solicitante autenticado pelo email.
        //       Delegar para mensagemService.listarMensagensCaso(casoId, solicitanteId).
        //       RN13: mensagens removidas terão conteudo = null na resposta.
        //       Retornar 200 OK com lista ordenada por dataEnvio ASC.
        throw new UnsupportedOperationException("Não implementado");
    }
}
