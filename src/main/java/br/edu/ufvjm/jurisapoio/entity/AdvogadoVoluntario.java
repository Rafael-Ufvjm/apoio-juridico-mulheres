package br.edu.ufvjm.jurisapoio.entity;

import br.edu.ufvjm.jurisapoio.enums.Disponibilidade;
import br.edu.ufvjm.jurisapoio.enums.StatusAprovacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

// RN10: totalCasosAtivos é contador desnormalizado — atualizado a cada atribuição/encerramento
// RN18: statusAprovacao controla acesso — apenas ATIVO recebe casos
@Entity
@Table(name = "advogados_voluntarios")
@DiscriminatorValue("ADVOGADO_VOLUNTARIO")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdvogadoVoluntario extends Usuario {

    @Column(nullable = false)
    private String nome;

    @Column(name = "numero_oab", nullable = false, unique = true)
    private String numeroOAB;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_aprovacao", nullable = false)
    private StatusAprovacao statusAprovacao;

    private String especialidades;

    @Enumerated(EnumType.STRING)
    private Disponibilidade disponibilidade;

    @Column(name = "total_casos_ativos", nullable = false)
    private Integer totalCasosAtivos;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    @Column(name = "justificativa_rejeicao", columnDefinition = "TEXT")
    private String justificativaRejeicao;

    @PrePersist
    @Override
    protected void prePersist() {
        super.prePersist();
        statusAprovacao = StatusAprovacao.PENDENTE;
        totalCasosAtivos = 0;
        disponibilidade = Disponibilidade.OFFLINE;
    }
}
