package com.ge.hc.healthlink;

import com.ge.hc.healthlink.config.HealthLinkBrokerServerConfig;
import com.ge.hc.healthlink.config.HealthLinkTopicConfig;
import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.handler.MqttMessageHandler;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import com.ge.hc.healthlink.transform.ElectricityMsgTransformer;
import com.ge.hc.healthlink.transform.LinkMsgTransformer;
import com.ge.hc.healthlink.transform.PowerStatusMsgTransformer;
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
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HealthlinkApplication {

	private static final Log LOGGER = LogFactory.getLog(HealthlinkApplication.class);

	@Autowired
	private HealthLinkBrokerServerConfig brokerServerConfig;

	@Autowired
	private HealthLinkTopicConfig topicConfig;

	@Autowired
	private MqttMessageHandler msgHandler;

	public static void main(String[] args) {
		SpringApplication.run(HealthlinkApplication.class, args);
	}

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();

		String serverUrl = brokerServerConfig.getHostname() + ":" + brokerServerConfig.getPort();
		LOGGER.debug("serverUrl: " + serverUrl);
		options.setServerURIs(new String[] { serverUrl });
		options.setUserName(brokerServerConfig.getUsername());
		options.setPassword(brokerServerConfig.getPassword().toCharArray());
		factory.setConnectionOptions(options);
		return factory;
	}

	@Bean
	public IntegrationFlow mqttInboundFlow() {
		return IntegrationFlows.from(mqttInboundHandler())
				.handle(p -> msgHandler.handleMessage(p))
				.get();
	}

	@Bean
	public MessageProducerSupport mqttInboundHandler() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("healthlinkMsgConsumer",
				mqttClientFactory());
		adapter.addTopic(topicConfig.getCurrent());
		adapter.addTopic(topicConfig.getLink());
		adapter.addTopic(topicConfig.getPower());
		adapter.addTopic(topicConfig.getLog());
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		return adapter;
	}

}

