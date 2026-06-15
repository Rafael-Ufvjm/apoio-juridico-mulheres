# apoio-juridico-mulheres

Plataforma de apoio jurídico gratuito para combate à violência contra as mulheres.

## Configuração do ambiente de desenvolvimento

### Pré-requisitos

- Java 21+
- Docker e Docker Compose
- IDE com suporte a Spring Boot (IntelliJ IDEA recomendado)

### Variáveis de ambiente (.env)

Copie o arquivo `.env.example` para `.env` e preencha os valores:

```bash
cp .env.example .env
```

**Atenção às regras das chaves:**

| Variável | Regra | Exemplo válido |
|---|---|---|
| `JWT_SEGREDO` | Deve ser uma string em **Base64 puro** (sem `_` ou `-`). Mínimo 32 bytes. | `ZGV2U2VncmVkb0Jhc2U2NE5hb1VzYXJFbVByb2R1Y2FvMTIzNDU2Nzg=` |
| `DB_USERNAME` | Deve coincidir com o usuário criado pelo Docker Compose | `jurisapoio` |
| `DB_PASSWORD` | Deve coincidir com a senha criada pelo Docker Compose | `jurisapoio` |
| `DB_NAME` | Deve coincidir com o banco criado pelo Docker Compose | `jurisapoio_dev` |

> **Por que Base64 puro no JWT_SEGREDO?**
> A biblioteca JJWT decodifica o segredo com Base64 padrão (RFC 4648), que não aceita os caracteres `_` e `-` (esses pertencem ao Base64 URL-safe). Usar qualquer um desses caracteres causa `DecodingException: Illegal base64 character` na inicialização.
>
> Para gerar um segredo seguro: `openssl rand -base64 64`

### Subindo o banco de dados

```bash
docker-compose up -d
```

O PostgreSQL sobe na porta `5433` com as credenciais definidas no `.env`.

> **Recriando o banco do zero:**
> Se as credenciais do `.env` foram alteradas após o container já ter sido criado, é necessário recriar o volume:
> ```bash
> docker-compose down -v
> docker-compose up -d
> ```

### Rodando a aplicação

Execute pela IDE com o perfil `dev` já ativo por padrão (`application.yml`).

O servidor sobe em `http://localhost:8080`.

## Testes via Postman

A collection com todos os endpoints e testes automatizados está em `postman/jurisapoio.postman_collection.json`.

Para importar: abra o Postman → **Import** → selecione o arquivo.

> O diretório `postman/` está no `.gitignore` — a collection não é versionada.