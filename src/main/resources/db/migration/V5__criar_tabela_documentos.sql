-- RN05: nome original do arquivo nunca em texto plano (nome_hasheado)
-- RN06: advogado só acessa com permissao_advogado = TRUE
-- RN08: máximo 20 documentos por caso (verificado em nível de serviço)
CREATE TABLE documentos (
    id                 UUID         NOT NULL,
    caso_id            UUID         NOT NULL,
    nome_hasheado      VARCHAR(255) NOT NULL,
    tipo_documento     VARCHAR(30)  NOT NULL,
    caminho            VARCHAR(500) NOT NULL,
    permissao_advogado BOOLEAN      NOT NULL DEFAULT FALSE,
    data_upload        TIMESTAMP    NOT NULL,
    CONSTRAINT pk_documentos        PRIMARY KEY (id),
    CONSTRAINT fk_documento_caso    FOREIGN KEY (caso_id) REFERENCES casos (id)
);
