-- ip_anonimizado: IP mascarado (ex: 192.168.1.xxx) — nunca IP completo
CREATE TABLE log_auditoria (
    id              UUID          NOT NULL,
    id_entidade     VARCHAR(36),
    tipo_entidade   VARCHAR(100),
    tipo_evento     VARCHAR(100)  NOT NULL,
    ip_anonimizado  VARCHAR(50),
    timestamp       TIMESTAMP     NOT NULL,
    detalhes        TEXT,
    CONSTRAINT pk_log_auditoria PRIMARY KEY (id)
);

-- refresh_tokens: controle de sessão — revogado=true invalida o token sem deletar
CREATE TABLE refresh_tokens (
    id          UUID         NOT NULL,
    token       VARCHAR(512) NOT NULL,
    expires_at  TIMESTAMP    NOT NULL,
    revogado    BOOLEAN      NOT NULL DEFAULT FALSE,
    usuario_id  UUID         NOT NULL,
    CONSTRAINT pk_refresh_tokens        PRIMARY KEY (id),
    CONSTRAINT uq_refresh_tokens_token  UNIQUE (token),
    CONSTRAINT fk_refresh_token_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);
