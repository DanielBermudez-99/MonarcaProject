package com.monarca.backendmonarca.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//En esta clase le delegamos el proceso de auntenticación a un filtro de Spring Security
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //Desactivamos el csrf para lograr acceder a la api
                .csrf(csrf -> csrf.disable())
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/user").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/user{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/product").hasAnyRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        //Encriptamos la contraseña
        return new BCryptPasswordEncoder();
    }
}
