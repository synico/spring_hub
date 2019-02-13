package com.ge.hc.healthlink.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hl_electricity_heartbeat")
public class ElectricityHeartbeat {

    @EmbeddedId
    private HeartbeatKey heartbeatKey;

    @Column(name = "status", columnDefinition = "CHAR(2)")
    private String status;

//    @Column(name = "asset_mac", columnDefinition = "CHAR(16)")
//    private String assetMAC;

//    @Column(name = "event_date")
//    private Integer eventDate;

    @Column(name = "electricity_value")
    private Integer electricity;

}
