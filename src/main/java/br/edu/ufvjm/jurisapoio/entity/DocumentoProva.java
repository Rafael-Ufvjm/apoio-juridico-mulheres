package br.edu.ufvjm.jurisapoio.entity;

import br.edu.ufvjm.jurisapoio.enums.CategoriaDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// RN05: nomeOriginalCriptografado armazena o nome via AES-256-GCM — nunca em texto plano
// RN06: permissaoAdvogado = false por padrão — vítima concede acesso explicitamente
// RN08: máximo 20 documentos por caso — verificado em DocumentoService
@Entity
@Table(name = "documentos_prova")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoProva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id", nullable = false)
    private Caso caso;

    // RN05: nome cifrado com AES-256-GCM via CriptografiaUtil
    @Column(name = "nome_original_criptografado", nullable = false, columnDefinition = "TEXT")
    private String nomeOriginalCriptografado;

    @Column(name = "hash_sha256", nullable = false, length = 64)
    private String hashSHA256;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaDocumento categoria;

    @Column(name = "tipo_mime", length = 100)
    private String tipoMIME;

    @Column(name = "tamanho_bytes")
    private Long tamanhoBytes;

    @Column(name = "url_s3", length = 500)
    private String urlS3;

    // RN06: false por padrão — somente vítima pode conceder acesso
    @Column(name = "permissao_advogado", nullable = false)
    private Boolean permissaoAdvogado;

    @PrePersist
    private void prePersist() {
        permissaoAdvogado = false;
    }
}
