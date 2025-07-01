package com.swiftling.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CorsSanitizingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        return chain.filter(exchange).doFirst(() -> {
            HttpHeaders headers = exchange.getResponse().getHeaders();
            headers.remove("Access-Control-Allow-Origin");
            headers.remove("Access-Control-Allow-Methods");
            headers.remove("Access-Control-Allow-Headers");
            headers.remove("Access-Control-Allow-Credentials");
            headers.remove("Access-Control-Expose-Headers");
            headers.remove("Access-Control-Max-Age");
        });
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
