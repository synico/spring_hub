package com.ge.hc.healthlink.factory;

import com.ge.hc.healthlink.config.HealthLinkBrokerServerConfig;
import com.ge.hc.healthlink.config.HealthLinkTopicConfig;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.stereotype.Component;

@Component
public class MqttClientFactory {

    private static final Log LOGGER = LogFactory.getLog(MqttClientFactory.class);

    @Autowired
    HealthLinkBrokerServerConfig brokerServerConfig;

    @Getter
    @Autowired
    HealthLinkTopicConfig topicConfig;

    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        String serverUrl = brokerServerConfig.getHostname() + ":" + brokerServerConfig.getPort();
        LOGGER.info("serverUrl: " + serverUrl);
        options.setServerURIs(new String[] { serverUrl });
        options.setUserName(brokerServerConfig.getUsername());
        options.setPassword(brokerServerConfig.getPassword().toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    public LoggingHandler logger(LoggingHandler.Level level, String loggerName) {
        LoggingHandler loggingHandler = new LoggingHandler(level);
        loggingHandler.setLoggerName(loggerName);
        return loggingHandler;
    }

}
