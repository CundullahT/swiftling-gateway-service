package com.swiftling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final String[] freeResourceURLs = {
            "/webjars/**",
            "/swagger-ui.html/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui",
            "/swagger-resources/**",
            "/swagger-resources",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swiftling-user-service/v3/**",
            "/swiftling-phrase-service/v3/**",
            "/swiftling-quiz-service/v3/**",
            "/swiftling-stats-service/v3/**"
    };

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        return http.authorizeExchange(authorize -> authorize
                        .pathMatchers(HttpMethod.GET, freeResourceURLs).permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();

    }

}
