package br.edu.ufvjm.jurisapoio.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // TODO: Adicionar dependência springdoc-openapi-starter-webmvc-ui ao pom.xml.
    //       Criar bean OpenAPI com título "JurisApoio API", versão e descrição do projeto.
    //       Configurar SecurityScheme do tipo HTTP Bearer JWT.
    //       Adicionar SecurityRequirement global para rotas autenticadas.
    //       URL da documentação: /swagger-ui.html e /v3/api-docs.
    //       Liberar essas rotas em SecurityConfig.
}
