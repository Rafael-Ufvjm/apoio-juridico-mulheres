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
        // TODO: Extrair UUID do advogado autenticado pelo email.
        //       Delegar para advogadoService.obterPerfil(advogadoId).
        //       Retornar 200 OK com AdvogadoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @PutMapping("/disponibilidade")
    @PreAuthorize("hasRole('ADVOGADO_VOLUNTARIO')")
    public ResponseEntity<AdvogadoResponse> atualizarDisponibilidade(
            @RequestParam Disponibilidade disponibilidade,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do advogado autenticado.
        //       RN15: advogado pode alterar disponibilidade se statusAprovacao == ATIVO.
        //       Delegar para advogadoService.atualizarDisponibilidade(advogadoId, disponibilidade).
        //       Retornar 200 OK com AdvogadoResponse atualizado.
        throw new UnsupportedOperationException("Não implementado");
    }

    @GetMapping("/casos")
    @PreAuthorize("hasRole('ADVOGADO_VOLUNTARIO')")
    public ResponseEntity<List<CasoResponse>> listarCasosDoAdvogado(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: Extrair UUID do advogado autenticado.
        //       Delegar para casoService.listarCasosDoAdvogado(advogadoId).
        //       Retornar 200 OK com lista de casos atribuídos ao advogado.
        throw new UnsupportedOperationException("Não implementado");
    }
}
