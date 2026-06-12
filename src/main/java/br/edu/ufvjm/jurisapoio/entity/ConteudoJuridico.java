package br.edu.ufvjm.jurisapoio.entity;

import br.edu.ufvjm.jurisapoio.enums.CategoriaConteudo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conteudo_juridico")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConteudoJuridico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String corpo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaConteudo categoria;

    private String tags;

    @Column(name = "nivel_linguagem")
    private String nivelLinguagem;

    @Column(name = "data_publicacao")
    private LocalDateTime dataPublicacao;

    @Column(name = "data_revisao_juridica")
    private LocalDateTime dataRevisaoJuridica;

    @Column(name = "revisado_por")
    private String revisadoPor;

    @Column(nullable = false)
    private Boolean publicado;

    @PrePersist
    private void prePersist() {
        publicado = false;
    }
}
