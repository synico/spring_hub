package com.ge.hc.healthlink.performance;

import com.ge.hc.healthlink.config.HealthLinkBrokerServerConfig;
import com.ge.hc.healthlink.config.HealthLinkMqttConfig;
import com.ge.hc.healthlink.config.HealthLinkTopicConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TopicDataLBTest {

    private static final Log LOGGER = LogFactory.getLog(TopicDataLBTest.class);

    @Autowired
    private HealthLinkBrokerServerConfig brokerServerConfig;

    @Autowired
    private HealthLinkTopicConfig topicConfig;

    @Autowired
    private HealthLinkMqttConfig mqttConfig;

    @Before
    public void setupConn() {
        LOGGER.info("x" + mqttConfig.getClientId());
    }

    private AtomicInteger theId = new AtomicInteger(0);

    @Test
    public void testMqttConn() throws InterruptedException{
        String serverURI = brokerServerConfig.getHostname() + ":" + brokerServerConfig.getPort();
        String clientId = mqttConfig.getClientId();
        LOGGER.info("\n" +
                "" +
                " serverURI: " + serverURI + "\n clientId: " + clientId);
        MqttConnectOptions connectOptions = this.createConnOptions();

        int N = 500;

        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(N);

        long epochTime = 1551166854;
        for(int i = 0; i < N; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                } catch (InterruptedException ex) {
                    LOGGER.info(ex.getMessage());
                }

                this.sendMessageById(connectOptions, clientId, epochTime);
            }).start();
            LOGGER.info("sending message");
        }
        startGate.countDown();
    }

    private void sendMessageById(MqttConnectOptions connectOptions, String clientId, long epochTime) {
        try {
            int id = theId.getAndAdd(1);
            String paddedId = this.createId(id);
            MqttClient client = this.createMqttClient(clientId + paddedId);
            client.connect(connectOptions);
            //create topic
            MqttTopic topic = client.getTopic(topicConfig.getData());
            //create message
            MqttMessage msg = createMessage(paddedId, Long.toString(epochTime + 1));
            //send msg
            MqttDeliveryToken token = topic.publish(msg);
            token.waitForCompletion();
            client.disconnect(1000);
            LOGGER.info("send completed.");
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private MqttClient createMqttClient(String clientId) {
        String serverURI = brokerServerConfig.getHostname() + ":" + brokerServerConfig.getPort();
        MqttClient client = null;
        try {
            client = new MqttClient(serverURI, clientId);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return client;
    }

    private MqttConnectOptions createConnOptions() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(brokerServerConfig.getUsername());
        connectOptions.setPassword(brokerServerConfig.getPassword().toCharArray());
        connectOptions.setConnectionTimeout(3000);
        return connectOptions;
    }

    private MqttMessage createMessage(String id, String time) {
        StringBuilder payload = new StringBuilder("C|BCDDC216B");
        payload.append(id);
        payload.append("|");
        payload.append(time);
        payload.append("|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0");

        MqttMessage msg = new MqttMessage();
        msg.setQos(2);
        msg.setRetained(false);
        msg.setPayload(payload.toString().getBytes());

        return msg;
    }

    private String createId(int id) {
        String paddedId = StringUtils.leftPad(Integer.toString(id), 3, "0");
        return paddedId;
    }

}
