package org.giriraj.filter;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Component  // Marks this class as a Spring-managed component
public class TransformRequestFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            // Logic to transform the request (can modify headers, query params, etc.)
            return chain.filter(exchange);  // Continue with the next filter in the chain
        };
    }
}
