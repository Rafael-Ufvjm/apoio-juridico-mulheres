-- Conteúdo educativo gerenciado pelo Administrador
-- revisado_por e dataRevisaoJuridica garantem qualidade jurídica do material
CREATE TABLE conteudo_juridico (
    id                    UUID         NOT NULL,
    titulo                VARCHAR(255) NOT NULL,
    corpo                 TEXT         NOT NULL,
    categoria             VARCHAR(50)  NOT NULL,
    tags                  VARCHAR(500),
    nivel_linguagem       VARCHAR(30),
    data_publicacao       TIMESTAMP,
    data_revisao_juridica TIMESTAMP,
    revisado_por          VARCHAR(255),
    publicado             BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_conteudo_juridico PRIMARY KEY (id)
);
