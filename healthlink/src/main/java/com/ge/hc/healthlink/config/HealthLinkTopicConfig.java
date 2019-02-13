package com.ge.hc.healthlink.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "healthlink.topic")
public class HealthLinkTopicConfig {

    private String log;

    private String event;

    private String power;

    private String link;

    private String current;

    private String data;

}
