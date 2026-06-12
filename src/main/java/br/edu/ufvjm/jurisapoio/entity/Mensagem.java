package br.edu.ufvjm.jurisapoio.entity;

import br.edu.ufvjm.jurisapoio.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

// RN13: conteudoCriptografado setado NULL após encerramento do caso
// remetentePerfil identifica quem enviou sem FK direta (preserva anonimato na consulta)
@Entity
@Table(name = "mensagens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id", nullable = false)
    private Caso caso;

    @Enumerated(EnumType.STRING)
    @Column(name = "remetente_perfil", nullable = false)
    private PerfilUsuario remetentePerfil;

    @Column(name = "data_envio", nullable = false, updatable = false)
    private LocalDateTime dataEnvio;

    // RN13: conteúdo criptografado em AES-256-GCM; removido ao encerrar o caso
    @Column(name = "conteudo_criptografado", columnDefinition = "TEXT")
    private String conteudoCriptografado;

    @Column(name = "conteudo_removido", nullable = false)
    private Boolean conteudoRemovido;

    @PrePersist
    private void prePersist() {
        dataEnvio = LocalDateTime.now();
        conteudoRemovido = false;
    }
}
