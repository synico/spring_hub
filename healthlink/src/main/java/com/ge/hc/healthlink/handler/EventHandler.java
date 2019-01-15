package com.ge.hc.healthlink.handler;

import com.ge.hc.healthlink.HealthlinkApplication;
import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.factory.MqttClientFactory;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class EventHandler {

    private static final Log LOGGER = LogFactory.getLog(EventHandler.class);

    @Autowired
    private MqttClientFactory mqttClientFactory;

    @Autowired
    private ElectricityMessageRepository electricityMsgRepository;

    @Bean
    public IntegrationFlow mqttEventFlow() {
        return IntegrationFlows.from(mqttEventInbound())
                .transform(p -> {
                    ElectricityMessage msg = new ElectricityMessage();
                    msg.setMessageContent(p.toString());
                    electricityMsgRepository.save(msg);
                    return msg.toString();
                })
                .handle(mqttClientFactory.logger(LoggingHandler.Level.INFO, HealthlinkApplication.class.getName()))
                .get();
    }

    @Bean
    public MessageProducerSupport mqttEventInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthMsgConsumer", mqttClientFactory.mqttClientFactory(), mqttClientFactory.getTopicConfig().getEvent());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        return adapter;
    }

}
