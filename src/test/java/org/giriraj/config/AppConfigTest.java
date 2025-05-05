package org.giriraj.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppConfigTest {

    @Test
    void testPropertyConfigBeanCreation() {
        // Create an instance of AppConfig
        AppConfig appConfig = new AppConfig();

        // Retrieve the PropertySourcesPlaceholderConfigurer bean
        PropertySourcesPlaceholderConfigurer bean = appConfig.propertyConfig();

        // Assert that the bean is not null
        assertNotNull(bean, "PropertySourcesPlaceholderConfigurer bean should not be null");
    }
}