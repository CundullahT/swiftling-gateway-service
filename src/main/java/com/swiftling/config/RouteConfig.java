package com.swiftling.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthHeaderFilter authHeaderFilter) {

        return builder.routes()
                .route("swiftling-phrase-service", r -> r
                        .path("/swiftling-phrase-service/**")
                        .filters(f -> f
                                .filter(authHeaderFilter)
                                .rewritePath("/swiftling-phrase-service/(?<path>.*)", "/${path}"))
                        .uri("lb://swiftling-phrase-service"))
                .route("user-service", r -> r
                        .path("/user-service/**")
                        .filters(f -> f
                                .filter(authHeaderFilter)
                                .rewritePath("/user-service/(?<path>.*)", "/${path}"))
                        .uri("lb://user-service"))
                .build();

    }

}
