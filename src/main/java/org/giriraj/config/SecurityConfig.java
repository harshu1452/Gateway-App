package org.giriraj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration // Marks this class as a Spring configuration class
public class SecurityConfig {

    @Bean // Defines a Spring bean for configuring security filters
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(auth -> auth
                        .pathMatchers("/public/**", "/actuator/**").permitAll() // Allow public and actuator endpoints without authentication
                        .anyExchange().authenticated() // Require authentication for all other requests
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for API Gateway since it mostly handles stateless requests
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define custom logout URL
                )
                .build(); // Build and return the security filter chain
    }
}