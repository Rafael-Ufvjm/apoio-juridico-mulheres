package br.edu.ufvjm.jurisapoio.entity;

import br.edu.ufvjm.jurisapoio.enums.StatusCaso;
import br.edu.ufvjm.jurisapoio.enums.TipoViolencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

// RN01: vitima só pode ter 1 caso ativo — verificado em CasoService
// RN10: advogado máximo 5 casos ativos — verificado em CasoService
// RN12: resultado obrigatório no encerramento — verificado em CasoService
@Entity
@Table(name = "casos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_violencia")
    private TipoViolencia tipoViolencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCaso status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vitima_id", nullable = false)
    private Vitima vitima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advogado_voluntario_id")
    private AdvogadoVoluntario advogadoVoluntario;

    @Column(name = "timestamp_abertura", nullable = false, updatable = false)
    private LocalDateTime timestampAbertura;

    @Column(name = "timestamp_encerramento")
    private LocalDateTime timestampEncerramento;

    // RN12: campo obrigatório ao encerrar
    @Column(columnDefinition = "TEXT")
    private String resultado;

    @Column(name = "chave_criptografia_chat", length = 500)
    private String chaveCriptografiaChat;

    @PrePersist
    private void prePersist() {
        timestampAbertura = LocalDateTime.now();
        status = StatusCaso.AGUARDANDO;
    }
}
