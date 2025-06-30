package com.swiftling.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthHeaderFilter implements GatewayFilter {

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/account/signup",
            "/api/v1/account/enable",
            "/api/v1/account/forgot-pass",
            "/api/v1/account/reset-pass"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String rawPath = request.getURI().getPath();

        boolean isPublicPath = PUBLIC_PATHS.stream().anyMatch(rawPath::endsWith);

        if (!isPublicPath && request.getHeaders().containsKey("Authorization")) {

            String authorizationHeaderValue = request.getHeaders().getFirst("Authorization");

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("Authorization", authorizationHeaderValue)
                    .build();
            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        }

        return chain.filter(exchange);
    }

}
