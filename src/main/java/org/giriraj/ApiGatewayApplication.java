package org.giriraj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for the Spring Boot API Gateway.
 * This class serves as the entry point to start the application.
 */
@SpringBootApplication  // Marks this as a Spring Boot application
public class ApiGatewayApplication {

    public static void main(String[] args) {
        // Launch the Spring Boot application
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
