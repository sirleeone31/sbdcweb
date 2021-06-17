package com.sbdc.sbdcweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "sbdc.app")
@Data
public class ApplicationProperties {
    private String frontServerAPI;
    private String jwtSecret;
    private String fileServer;
    private String dataServer;
    private Long jwtExpiration;
}