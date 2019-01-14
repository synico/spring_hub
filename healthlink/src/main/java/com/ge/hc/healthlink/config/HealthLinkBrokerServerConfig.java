package com.ge.hc.healthlink.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "healthlink.broker-server")
public class HealthLinkBrokerServerConfig {

    private String hostname;

    private Integer port;

    private String username;

    private String password;
}
