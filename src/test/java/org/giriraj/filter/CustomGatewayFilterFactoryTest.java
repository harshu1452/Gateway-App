package org.giriraj.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomGatewayFilterFactoryTest {

    private CustomGatewayFilterFactory filterFactory;
    private CustomGatewayFilterFactory.Config config;

    @BeforeEach
    void setUp() {
        // Initialize the filter factory and configuration before each test
        filterFactory = new CustomGatewayFilterFactory();
        config = new CustomGatewayFilterFactory.Config();
        config.setNewHeader("TestValue"); // Set a test header value
    }

    @Test
    void testFilterAddsHeader() {
        // Arrange: Create a filter instance and a mock request exchange
        GatewayFilter filter = filterFactory.apply(config);
        MockServerWebExchange exchange = MockServerWebExchange.from(
                org.springframework.mock.http.server.reactive.MockServerHttpRequest.get("/test").build()
        );
        GatewayFilterChain chain = mock(GatewayFilterChain.class);
        when(chain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty()); // Mock filter chain behavior

        // Act: Apply the filter and execute it
        Mono<Void> result = filter.filter(exchange, chain);

        // Assert: Verify the filter execution
        StepVerifier.create(result).verifyComplete(); // Ensure the chain proceeds
        assertThat(exchange.getRequest().getHeaders().getFirst("X-New-Header"))
                .isEqualTo("TestValue"); // Verify the header was added
        verify(chain, times(1)).filter(any(ServerWebExchange.class)); // Ensure the chain continues
    }

    @Test
    void testGetConfigClass() {
        // Assert: Ensure the correct configuration class is returned
        assertThat(filterFactory.getConfigClass()).isEqualTo(CustomGatewayFilterFactory.Config.class);
    }

    @Test
    void testGetName() {
        // Assert: Verify that the filter factory name is correct
        assertThat(filterFactory.getName()).isEqualTo("CustomHeader");
    }
}