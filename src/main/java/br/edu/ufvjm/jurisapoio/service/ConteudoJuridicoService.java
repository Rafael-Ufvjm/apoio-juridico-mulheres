package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.ConteudoJuridicoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.ConteudoJuridicoResponse;
import br.edu.ufvjm.jurisapoio.enums.CategoriaConteudo;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.ConteudoJuridicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConteudoJuridicoService {

    private final ConteudoJuridicoRepository conteudoJuridicoRepository;

    @Transactional(readOnly = true)
    public List<ConteudoJuridicoResponse> listarPublicados() {
        // TODO: Buscar via conteudoJuridicoRepository.findAllByPublicadoTrueOrderByDataPublicacaoDesc().
        //       Mapear para ConteudoJuridicoResponse e retornar lista.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional(readOnly = true)
    public List<ConteudoJuridicoResponse> listarPorCategoria(CategoriaConteudo categoria) {
        // TODO: Buscar via conteudoJuridicoRepository.findAllByCategoriaAndPublicadoTrue().
        //       Mapear para ConteudoJuridicoResponse e retornar lista.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional(readOnly = true)
    public ConteudoJuridicoResponse buscarPorId(UUID id) {
        // TODO: Buscar ConteudoJuridico pelo id ou lançar ResourceNotFoundException.
        //       Retornar apenas se publicado = true (ou admin pode ver todos — verificar perfil).
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public ConteudoJuridicoResponse criar(ConteudoJuridicoRequest request) {
        // TODO: Criar ConteudoJuridico com publicado = false (via @PrePersist).
        //       Persistir e retornar ConteudoJuridicoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public ConteudoJuridicoResponse publicar(UUID id) {
        // TODO: Buscar ConteudoJuridico ou lançar ResourceNotFoundException.
        //       Verificar que revisadoPor está preenchido antes de publicar.
        //       Setar publicado = true e dataPublicacao = now().
        //       Persistir e retornar ConteudoJuridicoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }
}
