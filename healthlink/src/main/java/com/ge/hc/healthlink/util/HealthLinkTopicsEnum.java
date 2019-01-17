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
    LOG("log"),
    /**
     * healthlink.topic.event
     */
    EVENT("event"),
    /**
     * healthlink.topic.power
     */
    POWER("power"),
    /**
     * healthlink.topic.link
     */
    LINK("link"),
    /**
     * healthlink.topic.current
     */
    CURRENT("current");

    @Getter
    @Setter
    private String topicName;

    HealthLinkTopicsEnum(String topicName) {
        this.topicName = topicName;
    }

}
