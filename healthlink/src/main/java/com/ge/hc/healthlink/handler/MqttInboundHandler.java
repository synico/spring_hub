package com.ge.hc.healthlink.handler;

import com.ge.hc.healthlink.config.HealthLinkBrokerServerConfig;
import com.ge.hc.healthlink.config.HealthLinkTopicConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

public class MqttInboundHandler {

    private static final Log LOGGER = LogFactory.getLog(MqttInboundHandler.class);

    @Autowired
    private HealthLinkTopicConfig topicConfig;

    @Autowired
    private HealthLinkBrokerServerConfig brokerServerConfig;

    @Bean
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

    @Bean
    public MessageProducerSupport mqttLogInboundHandler() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthlinkMsgConsumer",
                mqttClientFactory(),
                topicConfig.getLog());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        return adapter;
    }

    @Bean
    public MessageProducerSupport mqttCurrentInboundHandler() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthlinkMsgConsumer",
                mqttClientFactory(),
                topicConfig.getCurrent());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.addTopic();
        return adapter;
    }

    @Bean
    public MessageProducerSupport mqttEventInboundHandler() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthlinkMsgConsumer",
                mqttClientFactory(),
                topicConfig.getEvent());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        return adapter;
    }

    @Bean
    public MessageProducerSupport mqttLinkInboundHandler() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthlinkMsgConsumer",
                mqttClientFactory(),
                topicConfig.getLink());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        return adapter;
    }

    public LoggingHandler logger(LoggingHandler.Level level, String loggerName) {
        LoggingHandler loggingHandler = new LoggingHandler(level);
        loggingHandler.setLoggerName(loggerName);
        return loggingHandler;
    }

}
