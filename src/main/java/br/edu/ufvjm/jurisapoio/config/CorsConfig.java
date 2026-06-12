package br.edu.ufvjm.jurisapoio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // TODO: Criar CorsConfiguration permitindo origens do frontend (variável ${cors.origens-permitidas}).
        //       Métodos: GET, POST, PUT, DELETE, OPTIONS.
        //       Headers: Authorization, Content-Type.
        //       Expor header Authorization na resposta.
        //       Registrar em UrlBasedCorsConfigurationSource para "/**".
        //       Referenciar este bean em SecurityConfig.cors(c -> c.configurationSource(...)).
        throw new UnsupportedOperationException("Não implementado");
    }
}
