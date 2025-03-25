package com.swiftling.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthHeaderFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (request.getHeaders().containsKey("Authorization")) {

            String authorizationHeaderValue = request.getHeaders().getFirst("Authorization");

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("Authorization", authorizationHeaderValue)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        }

        return chain.filter(exchange);

    }

}
