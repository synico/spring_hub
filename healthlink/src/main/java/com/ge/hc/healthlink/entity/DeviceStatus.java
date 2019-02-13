package com.ge.hc.healthlink.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "hl_device_status")
public class DeviceStatus {

    @EmbeddedId
    private HeartbeatKey heartbeatKey;

    @Column(name = "status", columnDefinition = "CHAR(2)")
    private String status;

}
