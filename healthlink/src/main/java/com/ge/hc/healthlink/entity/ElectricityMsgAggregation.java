package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hl_electricity_aggregation")
public class ElectricityMsgAggregation {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "aggregation_id", columnDefinition = "CHAR(32)")
    private String id;

    @Column(name = "asset_mac", columnDefinition = "CHAR(16)")
    private String assetMAC;

    @Column(name = "event_date")
    private String eventDate;

    @Column(name = "total_electricity")
    private Integer totalElectricity;

    @Column(name = "ap_mac", columnDefinition = "CHAR(16)")
    private String apMAC;

}
