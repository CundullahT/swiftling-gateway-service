package com.swiftling.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CorsHeaderSanitizerFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();

            headers.clear();

//            headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
//            headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS);
//            headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS);
//            headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS);

//            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://cundi.onthewifi.com:5000");
//            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
//            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,OPTIONS");

        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
