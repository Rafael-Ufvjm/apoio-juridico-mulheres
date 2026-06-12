-- RN13: conteúdo das mensagens removido após encerramento do caso
CREATE TABLE mensagens (
    id                UUID      NOT NULL,
    caso_id           UUID      NOT NULL,
    remetente_id      UUID      NOT NULL,
    conteudo          TEXT,
    data_envio        TIMESTAMP NOT NULL,
    conteudo_removido BOOLEAN   NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_mensagens            PRIMARY KEY (id),
    CONSTRAINT fk_mensagem_caso        FOREIGN KEY (caso_id)      REFERENCES casos (id),
    CONSTRAINT fk_mensagem_remetente   FOREIGN KEY (remetente_id) REFERENCES usuarios (id)
);
