package com.ge.hc.healthlink;

import com.ge.hc.healthlink.config.HealthLinkBrokerServerConfig;
import com.ge.hc.healthlink.config.HealthLinkTopicConfig;
import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

@SpringBootApplication
public class HealthlinkApplication {

	private static final Log LOGGER = LogFactory.getLog(HealthlinkApplication.class);

	@Autowired
	private HealthLinkBrokerServerConfig brokerServerConfig;

	@Autowired
	private HealthLinkTopicConfig topicConfig;

	@Autowired
	private ElectricityMessageRepository electricityMsgRepository;

	public static void main(String[] args) {
		SpringApplication.run(HealthlinkApplication.class, args);
	}

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		LOGGER.info("hostname: " + brokerServerConfig.getHostname());
		options.setServerURIs(new String[] { brokerServerConfig.getHostname() + ":" + brokerServerConfig.getPort() });
		options.setUserName(brokerServerConfig.getUsername());
		options.setPassword(brokerServerConfig.getPassword().toCharArray());
		factory.setConnectionOptions(options);
		return factory;
	}

	@Bean
	public IntegrationFlow mqttInFlow() {
		return IntegrationFlows.from(mqttInbound())
//				.transform(p -> p + ", received from MQTT")
				.transform(p -> {
					ElectricityMessage msg = new ElectricityMessage();
					msg.setMessageContent(p.toString());
					electricityMsgRepository.save(msg);
					return msg.toString();
				})
				.handle(logger(LoggingHandler.Level.INFO, HealthlinkApplication.class.getName()))
				.get();
	}

	@Bean
	public MessageProducerSupport mqttInbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthlinkMsgConsumer",
				mqttClientFactory(), topicConfig.getLog());
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
//		adapter.setQos(1);
		return adapter;
	}

	public LoggingHandler logger(LoggingHandler.Level level, String loggerName) {
		LoggingHandler loggingHandler = new LoggingHandler(level);
		loggingHandler.setLoggerName(loggerName);
		return loggingHandler;
	}

}

