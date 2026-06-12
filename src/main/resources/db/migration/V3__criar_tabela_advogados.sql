-- RN18: admin não aprova OAB irregular — justificativa_rejeicao obrigatória na rejeição
CREATE TABLE advogados (
    id                     UUID         NOT NULL,
    nome                   VARCHAR(255) NOT NULL,
    numero_oab             VARCHAR(30)  NOT NULL,
    status_advogado        VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
    especialidade          VARCHAR(100),
    data_aprovacao         TIMESTAMP,
    justificativa_rejeicao TEXT,
    CONSTRAINT pk_advogados         PRIMARY KEY (id),
    CONSTRAINT uq_advogados_oab     UNIQUE (numero_oab),
    CONSTRAINT fk_advogado_usuario  FOREIGN KEY (id) REFERENCES usuarios (id)
);
