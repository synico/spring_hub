package com.ge.hc.healthlink.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "healthlink.mqtt.consumer")
public class HealthLinkMqttConfig {

    private String clientId;

}
