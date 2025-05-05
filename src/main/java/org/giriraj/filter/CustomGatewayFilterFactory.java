package org.giriraj.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Custom Gateway Filter Factory to transform incoming requests.
 */
@Configuration // Marks this class as a Spring-managed configuration component
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    /**
     * Configuration properties for the custom filter.
     */
    public static class Config {
        private String newHeader; // Header value to be added to the request

        // Getter and setter for newHeader
        public String getNewHeader() {
            return newHeader;
        }

        public void setNewHeader(String newHeader) {
            this.newHeader = newHeader;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(exchange.getRequest().mutate()
                                .header("X-New-Header", config.getNewHeader()) // Add custom header to the request
                                .build())
                        .build();
                return chain.filter(mutatedExchange); // Continue the filter chain with the modified exchange
            }
        };
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class; // Defines the configuration class for this filter
    }

    /**
     * Returns the unique name of the custom filter.
     */
    public String getName() {
        return "CustomHeader"; // Custom filter identifier used in Gateway configuration
    }
}