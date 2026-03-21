package com.belair.buvette.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.belair.buvette.application.order", "com.belair.buvette.infrastructure.order"})
public class TestConfig {
    // Reusable minimal auto-configuration for tests that need a Spring context.
}
