package org.giriraj.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformRequestFilterTest {

    private TransformRequestFilter filter;
    private CustomGatewayFilterFactory.Config config;

    @BeforeEach
    public void setUp() {
        // Initialize the filter and configuration before each test
        filter = new TransformRequestFilter();
        config = new CustomGatewayFilterFactory.Config();
    }

    @Test
    public void testApply_WithHeaderValue() {
        // Arrange: Set a test header value in the configuration
        config.setNewHeader("TestHeaderValue");
        GatewayFilterChain chain = Mockito.mock(GatewayFilterChain.class);
        ServerWebExchange exchange = MockServerWebExchange.from(
                org.springframework.mock.http.server.reactive.MockServerHttpRequest.get("/").build()
        );

        // Mock the filter chain to return an empty Mono
        Mockito.when(chain.filter(Mockito.any())).thenReturn(Mono.empty());

        // Act: Apply the filter and process the request
        filter.apply(config).filter(exchange, chain).block();

        // Capture the mutated exchange after filtering
        ArgumentCaptor<ServerWebExchange> exchangeCaptor = ArgumentCaptor.forClass(ServerWebExchange.class);
        Mockito.verify(chain).filter(exchangeCaptor.capture());
        var mutatedExchange = exchangeCaptor.getValue();

        // Assert: Ensure that the new header was not added (as TransformRequestFilter does not modify headers)
        assertEquals(0, mutatedExchange.getRequest().getHeaders().size());
    }

    @Test
    public void testApply_WithoutHeaderValue() {
        // Arrange: Set the new header value to null
        config.setNewHeader(null);
        GatewayFilterChain chain = Mockito.mock(GatewayFilterChain.class);
        ServerWebExchange exchange = MockServerWebExchange.from(
                org.springframework.mock.http.server.reactive.MockServerHttpRequest.get("/").build()
        );

        // Mock the filter chain to return an empty Mono
        Mockito.when(chain.filter(Mockito.any())).thenReturn(Mono.empty());

        // Act: Apply the filter and process the request
        filter.apply(config).filter(exchange, chain).block();

        // Assert: Verify that the chain was called without modifying headers
        Mockito.verify(chain).filter(Mockito.any());
    }
}
