package org.giriraj;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ApiGateWayApplicationTest {

    @Test
    public void testMainMethod() {
        // Mock the static method SpringApplication.run to prevent actual application startup
        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {

            // Ensure the main method runs without throwing any exceptions
            assertDoesNotThrow(() -> ApiGatewayApplication.main(new String[]{}),
                    "Main method should run without throwing exceptions.");

            // Verify that SpringApplication.run was called with the expected arguments
            mockedSpringApplication.verify(() -> SpringApplication.run(ApiGatewayApplication.class, new String[]{}));
        }
    }
}