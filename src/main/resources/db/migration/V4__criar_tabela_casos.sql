-- RN01: vitima pode ter apenas 1 caso ativo (verificado em nível de serviço)
-- RN10: advogado pode ter no máximo 5 casos ativos (verificado em nível de serviço)
-- RN12: resultado obrigatório no encerramento
CREATE TABLE casos (
    id                UUID      NOT NULL,
    vitima_id         UUID      NOT NULL,
    advogado_id       UUID,
    descricao         TEXT      NOT NULL,
    status_caso       VARCHAR(20) NOT NULL DEFAULT 'ABERTO',
    data_criacao      TIMESTAMP NOT NULL,
    data_encerramento TIMESTAMP,
    resultado         TEXT,
    CONSTRAINT pk_casos         PRIMARY KEY (id),
    CONSTRAINT fk_caso_vitima   FOREIGN KEY (vitima_id)   REFERENCES vitimas (id),
    CONSTRAINT fk_caso_advogado FOREIGN KEY (advogado_id) REFERENCES advogados (id)
);
