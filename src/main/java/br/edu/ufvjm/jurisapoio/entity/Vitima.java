package br.edu.ufvjm.jurisapoio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// RN02: nunca armazenar nome real, CPF ou qualquer dado identificador — apenas nomeAnonimo
@Entity
@Table(name = "vitimas")
@DiscriminatorValue("VITIMA")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Vitima extends Usuario {

    @Column(name = "nome_anonimo", nullable = false)
    private String nomeAnonimo;

    @Column(name = "estado_residencia")
    private String estadoResidencia;

    @Column(name = "aceitou_termos", nullable = false)
    private Boolean aceitouTermos;

    @PrePersist
    @Override
    protected void prePersist() {
        super.prePersist();
        aceitouTermos = false;
    }
}
