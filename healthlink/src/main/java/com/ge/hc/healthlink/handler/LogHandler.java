package com.ge.hc.healthlink.handler;

import com.ge.hc.healthlink.HealthlinkApplication;
import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.factory.MqttClientFactory;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.stereotype.Component;

public class LogHandler {

    private static final Log LOGGER = LogFactory.getLog(LogHandler.class);

    @Autowired
    private MqttClientFactory mqttClientFactory;

    @Autowired
    private ElectricityMessageRepository electricityMsgRepository;

    public MessageProducerSupport mqttLogInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthlinkMsgConsumer", mqttClientFactory.mqttClientFactory(), mqttClientFactory.getTopicConfig().getLog());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
//		adapter.setQos(1);
        return adapter;
    }

    public IntegrationFlow mqttLogInFlow() {
        return IntegrationFlows.from(mqttLogInbound())
//				.transform(p -> p + ", received from MQTT")
                .transform(p -> {
                    ElectricityMessage msg = new ElectricityMessage();
                    msg.setMessageContent(p.toString());
                    electricityMsgRepository.save(msg);
                    return msg.toString();
                })
                .handle(mqttClientFactory.logger(LoggingHandler.Level.INFO, HealthlinkApplication.class.getName()))
                .get();
    }

}
