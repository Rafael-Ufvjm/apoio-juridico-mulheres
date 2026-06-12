-- RN13: conteúdo removido após encerramento — conteudo_criptografado setado NULL
-- remetente_perfil (VITIMA | ADVOGADO_VOLUNTARIO) identifica quem enviou sem FK direta
CREATE TABLE mensagens (
    id                     UUID      NOT NULL,
    caso_id                UUID      NOT NULL,
    remetente_perfil       VARCHAR(30) NOT NULL,
    data_envio             TIMESTAMP NOT NULL,
    conteudo_criptografado TEXT,
    conteudo_removido      BOOLEAN   NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_mensagens      PRIMARY KEY (id),
    CONSTRAINT fk_mensagem_caso  FOREIGN KEY (caso_id) REFERENCES casos (id)
);
