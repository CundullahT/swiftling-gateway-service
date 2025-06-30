package com.swiftling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final String[] freeResourceURLs = {
            "/actuator",
            "/actuator/**",
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
            "/swiftling-notification-service/v3/**"
    };

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        return http.authorizeExchange(authorize -> authorize
                        .pathMatchers(HttpMethod.GET, freeResourceURLs).permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8762",
                "http://cundi.onthewifi.com:8762",
                "http://localhost:5000",
                "http://cundi.onthewifi.com:5000",
                "https://swiftlingapp.com",
                "https://www.swiftlingapp.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
