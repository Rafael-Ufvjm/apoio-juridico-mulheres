package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.request.MensagemRequest;
import br.edu.ufvjm.jurisapoio.dto.response.MensagemResponse;
import br.edu.ufvjm.jurisapoio.entity.AdvogadoVoluntario;
import br.edu.ufvjm.jurisapoio.entity.Usuario;
import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<MensagemResponse> enviarMensagem(
            @PathVariable UUID casoId,
            @Valid @RequestBody MensagemRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Usuario remetente = usuarioRepository.findByEmailAndAtivoTrue(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        PerfilUsuario perfil = remetente instanceof AdvogadoVoluntario
                ? PerfilUsuario.ADVOGADO_VOLUNTARIO
                : PerfilUsuario.VITIMA;

        MensagemResponse response = mensagemService.enviarMensagem(casoId, remetente.getId(), perfil, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MensagemResponse>> listarMensagens(
            @PathVariable UUID casoId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Usuario solicitante = usuarioRepository.findByEmailAndAtivoTrue(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        return ResponseEntity.ok(mensagemService.listarMensagensCaso(casoId, solicitante.getId()));
    }
}