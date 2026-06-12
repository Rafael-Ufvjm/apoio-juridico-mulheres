-- RN05: nome original criptografado com AES-256-GCM — nunca em texto plano
-- RN06: advogado só acessa com permissao_advogado = TRUE
-- RN08: máximo 20 documentos por caso — verificado em DocumentoService
CREATE TABLE documentos_prova (
    id                         UUID         NOT NULL,
    caso_id                    UUID         NOT NULL,
    nome_original_criptografado TEXT        NOT NULL,
    hash_sha256                VARCHAR(64)  NOT NULL,
    categoria                  VARCHAR(30)  NOT NULL,
    tipo_mime                  VARCHAR(100),
    tamanho_bytes              BIGINT,
    url_s3                     VARCHAR(500),
    permissao_advogado         BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_documentos_prova      PRIMARY KEY (id),
    CONSTRAINT fk_documento_prova_caso  FOREIGN KEY (caso_id) REFERENCES casos (id)
);
