package com.ge.hc.healthlink.util;

import com.ge.hc.healthlink.entity.DeviceStatus;
import lombok.Getter;
import lombok.Setter;

public enum DeviceStatusEnum {

    /**
     *
     */
    POWERON(0),

    /**
     *
     */
    POWEROFF(1),

    /**
     *
     */
    STANDBY(2),

    /**
     *
     */
    RUNNING(3);

    @Getter
    @Setter
    private int statusCode;

    DeviceStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public static DeviceStatusEnum getStatusByCode(int statusCode) {
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
