package com.ge.hc.healthlink.util;

import lombok.Getter;
import lombok.Setter;

/**
 * refer to application-${tier}.yml healthlink.topic
 */
public enum HealthLinkTopicsEnum {
    /**
     * healthlink.topic.log
     */
    LOG("/HealthLink/log"),
    /**
     * healthlink.topic.event
     */
    EVENT("/HealthLink/event"),
    /**
     * healthlink.topic.power
     */
    POWER("/HealthLink/power"),
    /**
     * healthlink.topic.link
     */
    LINK("/HealthLink/link"),
    /**
     * healthlink.topic.current
     */
    DATA("/HealthLink/data"),
    /**
     * healthlink.topic.current
     */
    CURRENT("/HealthLink/current");

    @Getter
    @Setter
    private String topicName;

    HealthLinkTopicsEnum(String topicName) {
        this.topicName = topicName;
    }

    public static HealthLinkTopicsEnum getTopicByName(String name) {
        HealthLinkTopicsEnum theTopic = LOG;
        for(HealthLinkTopicsEnum topic : values()) {
            if(topic.topicName.equals(name)) {
                theTopic = topic;
                break;
            }
        }
        return theTopic;
    }

}
