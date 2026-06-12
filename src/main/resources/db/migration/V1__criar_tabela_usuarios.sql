-- Herança JOINED: tabela pai + uma tabela por subtipo
-- RN02: vitima não armazena nome real nem CPF — apenas nomeAnonimo
-- RN18: statusAprovacao controla acesso do advogado voluntário

CREATE TABLE usuarios (
    id            UUID         NOT NULL,
    email         VARCHAR(255) NOT NULL,
    senha_hash    VARCHAR(255) NOT NULL,
    perfil        VARCHAR(31)  NOT NULL,
    data_cadastro TIMESTAMP    NOT NULL,
    ativo         BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_usuarios       PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_email UNIQUE (email)
);

CREATE TABLE vitimas (
    id               UUID         NOT NULL,
    nome_anonimo     VARCHAR(100) NOT NULL,
    estado_residencia VARCHAR(50),
    aceitou_termos   BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_vitimas        PRIMARY KEY (id),
    CONSTRAINT fk_vitima_usuario FOREIGN KEY (id) REFERENCES usuarios (id)
);

-- RN10: total_casos_ativos é contador desnormalizado — atualizado a cada atribuição/encerramento
CREATE TABLE advogados_voluntarios (
    id                     UUID         NOT NULL,
    nome                   VARCHAR(255) NOT NULL,
    numero_oab             VARCHAR(30)  NOT NULL,
    status_aprovacao       VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
    especialidades         VARCHAR(500),
    disponibilidade        VARCHAR(20),
    total_casos_ativos     INTEGER      NOT NULL DEFAULT 0,
    data_aprovacao         TIMESTAMP,
    justificativa_rejeicao TEXT,
    CONSTRAINT pk_advogados_voluntarios  PRIMARY KEY (id),
    CONSTRAINT uq_advogados_numero_oab   UNIQUE (numero_oab),
    CONSTRAINT fk_advogado_usuario       FOREIGN KEY (id) REFERENCES usuarios (id)
);

CREATE TABLE administradores (
    id               UUID    NOT NULL,
    nivel_acesso     INTEGER NOT NULL DEFAULT 1,
    two_factor_ativo BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_administradores        PRIMARY KEY (id),
    CONSTRAINT fk_administrador_usuario  FOREIGN KEY (id) REFERENCES usuarios (id)
);
