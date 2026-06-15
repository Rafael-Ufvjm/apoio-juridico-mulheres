package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.request.ConteudoJuridicoRequest;
import br.edu.ufvjm.jurisapoio.dto.response.ConteudoJuridicoResponse;
import br.edu.ufvjm.jurisapoio.entity.ConteudoJuridico;
import br.edu.ufvjm.jurisapoio.enums.CategoriaConteudo;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.exception.ResourceNotFoundException;
import br.edu.ufvjm.jurisapoio.repository.ConteudoJuridicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConteudoJuridicoService {

    private final ConteudoJuridicoRepository conteudoJuridicoRepository;

    @Transactional(readOnly = true)
    public List<ConteudoJuridicoResponse> listarPublicados() {
        return conteudoJuridicoRepository.findAllByPublicadoTrueOrderByDataPublicacaoDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConteudoJuridicoResponse> listarPorCategoria(CategoriaConteudo categoria) {
        return conteudoJuridicoRepository.findAllByCategoriaAndPublicadoTrue(categoria)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConteudoJuridicoResponse buscarPorId(UUID id) {
        ConteudoJuridico conteudo = conteudoJuridicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo não encontrado"));
        if (!conteudo.getPublicado()) {
            throw new ResourceNotFoundException("Conteúdo não encontrado");
        }
        return toResponse(conteudo);
    }

    @Transactional
    public ConteudoJuridicoResponse criar(ConteudoJuridicoRequest request) {
        ConteudoJuridico conteudo = ConteudoJuridico.builder()
                .titulo(request.titulo())
                .corpo(request.corpo())
                .categoria(request.categoria())
                .tags(request.tags())
                .nivelLinguagem(request.nivelLinguagem())
                .revisadoPor(request.revisadoPor())
                .build();
        return toResponse(conteudoJuridicoRepository.save(conteudo));
    }

    @Transactional
    public ConteudoJuridicoResponse publicar(UUID id) {
        ConteudoJuridico conteudo = conteudoJuridicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo não encontrado"));
        if (conteudo.getRevisadoPor() == null || conteudo.getRevisadoPor().isBlank()) {
            throw new BusinessException("Conteúdo precisa ser revisado antes de ser publicado");
        }
        conteudo.setPublicado(true);
        conteudo.setDataPublicacao(LocalDateTime.now());
        return toResponse(conteudoJuridicoRepository.save(conteudo));
    }

    @Transactional
    public ConteudoJuridicoResponse atualizar(UUID id, ConteudoJuridicoRequest request) {
        ConteudoJuridico conteudo = conteudoJuridicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo não encontrado"));
        conteudo.setTitulo(request.titulo());
        conteudo.setCorpo(request.corpo());
        conteudo.setCategoria(request.categoria());
        conteudo.setTags(request.tags());
        conteudo.setNivelLinguagem(request.nivelLinguagem());
        conteudo.setRevisadoPor(request.revisadoPor());
        return toResponse(conteudoJuridicoRepository.save(conteudo));
    }

    @Transactional
    public void deletar(UUID id) {
        if (!conteudoJuridicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Conteúdo não encontrado");
        }
        conteudoJuridicoRepository.deleteById(id);
    }

    private ConteudoJuridicoResponse toResponse(ConteudoJuridico c) {
        return new ConteudoJuridicoResponse(
                c.getId(),
                c.getTitulo(),
                c.getCorpo(),
                c.getCategoria(),
                c.getTags(),
                c.getNivelLinguagem(),
                c.getDataPublicacao(),
                c.getRevisadoPor()
        );
    }
}