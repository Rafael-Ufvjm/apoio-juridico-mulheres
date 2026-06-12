-- RN02: vitima só expõe codinome — nome real e CPF nunca são armazenados
CREATE TABLE vitimas (
    id       UUID         NOT NULL,
    codinome VARCHAR(100) NOT NULL,
    CONSTRAINT pk_vitimas         PRIMARY KEY (id),
    CONSTRAINT fk_vitima_usuario  FOREIGN KEY (id) REFERENCES usuarios (id)
);
