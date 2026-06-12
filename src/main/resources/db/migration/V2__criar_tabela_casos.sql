-- RN01: vitima só pode ter 1 caso ativo — verificado em CasoService
-- RN10: advogado pode ter no máximo 5 casos ativos — verificado em CasoService
-- RN12: resultado obrigatório no encerramento — verificado em CasoService
CREATE TABLE casos (
    id                      UUID        NOT NULL,
    protocolo               VARCHAR(50),
    tipo_violencia          VARCHAR(30),
    status                  VARCHAR(20) NOT NULL DEFAULT 'AGUARDANDO',
    vitima_id               UUID        NOT NULL,
    advogado_voluntario_id  UUID,
    timestamp_abertura      TIMESTAMP   NOT NULL,
    timestamp_encerramento  TIMESTAMP,
    resultado               TEXT,
    chave_criptografia_chat VARCHAR(500),
    CONSTRAINT pk_casos             PRIMARY KEY (id),
    CONSTRAINT uq_casos_protocolo   UNIQUE (protocolo),
    CONSTRAINT fk_caso_vitima       FOREIGN KEY (vitima_id)             REFERENCES vitimas (id),
    CONSTRAINT fk_caso_advogado     FOREIGN KEY (advogado_voluntario_id) REFERENCES advogados_voluntarios (id)
);
