package com.devin.luminland.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SpringBeanConfig {

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventListener(Environment environment) {
        return event -> {
            String port = environment.getProperty("server.port", "8080");
            String contextPath = environment.getProperty("server.servlet.context-path", "");
            String accessUrl = "http://localhost:" + port + contextPath + "/chatui/index.html";
            System.out.println("\n🎉========================================🎉");
            System.out.println("✅ Application is ready!");
            System.out.println("🚀 Chat with you agent: " + accessUrl);
            System.out.println("🎉========================================🎉\n");
        };
    }
}
