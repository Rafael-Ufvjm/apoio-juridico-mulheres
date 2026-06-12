CREATE TABLE usuarios (
    id           UUID         NOT NULL,
    email        VARCHAR(255) NOT NULL,
    senha        VARCHAR(255) NOT NULL,
    tipo_perfil  VARCHAR(31)  NOT NULL,
    data_criacao TIMESTAMP    NOT NULL,
    ativo        BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_usuarios         PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_email   UNIQUE (email)
);
