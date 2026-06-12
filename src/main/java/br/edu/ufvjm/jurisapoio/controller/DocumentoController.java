package br.edu.ufvjm.jurisapoio.controller;

import br.edu.ufvjm.jurisapoio.dto.response.DocumentoResponse;
import br.edu.ufvjm.jurisapoio.enums.CategoriaDocumento;
import br.edu.ufvjm.jurisapoio.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/casos/{casoId}/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    @PostMapping
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<DocumentoResponse> uploadDocumento(
            @PathVariable UUID casoId,
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("categoria") CategoriaDocumento categoria,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID da vítima autenticada.
        //       RN05: nome original criptografado com AES-256-GCM no DocumentoService via CriptografiaUtil.
        //       RN08: limite de 20 documentos por caso verificado no DocumentoService.
        //       Delegar para documentoService.uploadDocumento(casoId, vitimaId, arquivo, categoria).
        //       Retornar 201 CREATED com DocumentoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @GetMapping
    public ResponseEntity<List<DocumentoResponse>> listarDocumentos(
            @PathVariable UUID casoId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do solicitante autenticado.
        //       RN06: advogado verá apenas documentos com permissaoAdvogado = true.
        //       Delegar para documentoService.listarPorCaso(casoId, solicitanteId).
        //       Retornar 200 OK com lista.
        throw new UnsupportedOperationException("Não implementado");
    }

    @GetMapping("/{documentoId}/download")
    public ResponseEntity<Resource> downloadDocumento(
            @PathVariable UUID casoId,
            @PathVariable UUID documentoId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do solicitante autenticado.
        //       RN06: documentoService verifica permissão do advogado antes de servir o arquivo.
        //       Delegar para documentoService.downloadDocumento(documentoId, solicitanteId).
        //       Retornar 200 OK com Content-Disposition: attachment.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PutMapping("/{documentoId}/permissao")
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<DocumentoResponse> concederPermissao(
            @PathVariable UUID casoId,
            @PathVariable UUID documentoId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: RN06: extrair UUID da vítima autenticada.
        //       Delegar para documentoService.concederPermissaoAdvogado(documentoId, vitimaId).
        //       Retornar 200 OK com DocumentoResponse atualizado.
        throw new UnsupportedOperationException("Não implementado");
    }

    @DeleteMapping("/{documentoId}/permissao")
    @PreAuthorize("hasRole('VITIMA')")
    public ResponseEntity<DocumentoResponse> revogarPermissao(
            @PathVariable UUID casoId,
            @PathVariable UUID documentoId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: RN06: extrair UUID da vítima autenticada.
        //       Delegar para documentoService.revogarPermissaoAdvogado(documentoId, vitimaId).
        //       Retornar 200 OK com DocumentoResponse atualizado.
        throw new UnsupportedOperationException("Não implementado");
    }
}
