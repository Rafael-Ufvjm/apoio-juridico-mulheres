package br.edu.ufvjm.jurisapoio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

// ipAnonimizado: nunca armazenar IP completo — usar AnonimizacaoUtil.anonimizarIp()
@Entity
@Table(name = "log_auditoria")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_entidade")
    private String idEntidade;

    @Column(name = "tipo_entidade")
    private String tipoEntidade;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(name = "ip_anonimizado")
    private String ipAnonimizado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(columnDefinition = "TEXT")
    private String detalhes;

    @PrePersist
    private void prePersist() {
        timestamp = LocalDateTime.now();
    }
}
