package com.ge.hc.healthlink.handler;

import com.ge.hc.healthlink.config.HealthLinkTopicConfig;
import com.ge.hc.healthlink.transform.ElectricityMsgTransformer;
import com.ge.hc.healthlink.transform.LinkMsgTransformer;
import com.ge.hc.healthlink.transform.PowerStatusMsgTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageHandler implements MessageHandler {

    private static final Log LOGGER = LogFactory.getLog(MqttMessageHandler.class);

    @Autowired
    private HealthLinkTopicConfig topicConfig;

    @Autowired
    private ElectricityMsgTransformer electricityMsgTransformer;

    @Autowired
    private PowerStatusMsgTransformer powerStatusMsgTransformer;

    @Autowired
    private LinkMsgTransformer linkMsgTransformer;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();
        LOGGER.info("topic: " + headers.get("mqtt_receivedTopic") + "####" + message.getPayload());
        String msgTopic = headers.get("mqtt_receivedTopic").toString();
        String msgPayload = message.getPayload().toString();
        switch (msgTopic) {
            case "/HealthLink/current":
                electricityMsgTransformer.transform(msgPayload);
                break;
            case "/HealthLink/power":
                powerStatusMsgTransformer.transform(msgPayload);
                break;
            case "/HealthLink/link":
                linkMsgTransformer.transform(msgPayload);
                break;
            default:
                LOGGER.info("msg: " + msgPayload);

        }
    }
}

