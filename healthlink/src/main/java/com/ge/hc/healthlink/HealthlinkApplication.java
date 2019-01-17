package com.ge.hc.healthlink;

import com.ge.hc.healthlink.factory.MqttClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;

import static com.ge.hc.healthlink.util.HealthLinkTopicsEnum.EVENT;

@EnableIntegration
@SpringBootApplication
public class HealthlinkApplication {

	@Autowired
	MqttClientFactory mqttClientFactory;

	public static void main(String[] args) {
		SpringApplication.run(HealthlinkApplication.class, args);
	}

	@Bean(name = "mqttEventFlow")
	public IntegrationFlow mqttEventFlow() {
		return IntegrationFlows.from(mqttClientFactory.msgInboundHandler(EVENT.getTopicName()))
				.transform(p -> p)
				.handle(mqttClientFactory.logger(LoggingHandler.Level.INFO, HealthlinkApplication.class.getName()))
				.get();
	}

}

