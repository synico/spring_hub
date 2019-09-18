package com.ge.hc.healthlink.util;

import lombok.Getter;
import lombok.Setter;

public enum DeviceStatusEnum {

    /**
     *
     */
    POWERON(1),

    /**
     *
     */
    POWEROFF(0),

    /**
     *
     */
    STANDBY(2),

    /**
     *
     */
    RUNNING(3),

    /**
     *
     */
    UNKNOWN(-1);

    @Getter
    @Setter
    private int statusCode;

    DeviceStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public DeviceStatusEnum getStatusByCode(int statusCode) {
        DeviceStatusEnum statusEnum = POWERON;
        for(DeviceStatusEnum status : values()) {
            if(status.getStatusCode() == statusCode) {
                statusEnum = status;
                break;
            }
        }
        return statusEnum;
    }

}
