package com.monarca.backendmonarca.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

//En esta clase le delegamos el proceso de auntenticación a un filtro de Spring Security
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //Desactivamos el csrf para lograr acceder a la api
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeRequests()
                .requestMatchers(HttpMethod.POST, "password-reset/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "password-reset/**").permitAll()
                .requestMatchers(HttpMethod.POST, "user/register/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/category/create").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/category/list").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/category/update/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/category/delete/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/user").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/product/list").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.POST, "/product/register").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/product/update/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/product/delete/{id}").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    Esta es la nueva forma de configurar el Cors
    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOrigins(java.util.List.of("http://localhost:5173"));
            cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(java.util.List.of("*"));
            return cors;
        };
    }
}
