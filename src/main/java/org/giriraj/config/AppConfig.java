package org.giriraj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration // Marks this class as a Spring configuration class
public class AppConfig {

    @Bean // Defines a Spring bean for property placeholder resolution
    public PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer(); // Enables support for ${...} placeholders in property files
    }
}