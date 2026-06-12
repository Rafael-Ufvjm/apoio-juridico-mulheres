package br.edu.ufvjm.jurisapoio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "administradores")
@DiscriminatorValue("ADMIN")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Administrador extends Usuario {

    @Column(name = "nivel_acesso", nullable = false)
    private Integer nivelAcesso;

    @Column(name = "two_factor_ativo", nullable = false)
    private Boolean twoFactorAtivo;

    @PrePersist
    @Override
    protected void prePersist() {
        super.prePersist();
        nivelAcesso = 1;
        twoFactorAtivo = false;
    }
}
