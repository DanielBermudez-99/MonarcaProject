package com.monarca.backendmonarca.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    //Configuramos el cors para que acepte cualquier origen
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //Definimos que origenes queremos permitir
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        //Definimos que metodos quiero permitir desde cors
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //Especificamos que son todos los controladores
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
