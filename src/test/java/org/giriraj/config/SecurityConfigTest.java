package org.giriraj.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Runs the test with a random port
@EnableWebFlux // Enables WebFlux configuration
@Import(SecurityConfig.class) // Imports the SecurityConfig for testing
public class SecurityConfigTest {

    @Autowired
    private WebTestClient webTestClient; // WebTestClient for testing reactive web applications

    @Test
    void checkHealthEndpointSuccess(){
        // Test to verify that the health endpoint is accessible and returns a 200 OK response
        webTestClient.get().uri("/actuator/health")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // Simulate an authenticated user with the role USER
    void authenticatedUserShouldAccessSecuredEndpoints() {
        // Test to verify that an authenticated user cannot access a secured endpoint
        webTestClient.get().uri("/secure-endpoint")
                .exchange()
                .expectStatus().isUnauthorized(); // Expect 401 Unauthorized
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // Simulate a logged-in user
    void logoutShouldRedirectToSuccessUrl() {
        // Test to verify that logout redirects the user to the login page
        webTestClient.post().uri("/logout")
                .exchange()
                .expectStatus().is3xxRedirection() // Expect a redirection response
                .expectHeader().location("/login?logout"); // Ensure redirection to login page with logout param
    }

    @Test
    void publicEndpointsShouldNotBeAccessible() {
        // Test to verify that an unknown public endpoint returns a 404 Not Found response
        webTestClient.get().uri("/public/test")
                .exchange()
                .expectStatus().isNotFound();
    }
}
