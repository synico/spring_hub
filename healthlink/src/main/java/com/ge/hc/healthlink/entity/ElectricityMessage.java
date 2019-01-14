package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "electricity_message")
@Data
public class ElectricityMessage {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "message_id", columnDefinition = "CHAR(32)")
    private String id;

    @Column(name = "message_content", columnDefinition = "VARCHAR(255)")
    private String messageContent;

    @Column(name = "asset_mac", columnDefinition = "CHAR(14)")
    private String assetMAC;

    @Column(name = "create_date")
    private Integer createDate;

    @Column(name = "electricity_value")
    private Integer electricity;

    @Column(name = "instant_power")
    private Integer instantPower;

    @Column(name = "field_intensity")
    private Integer fieldIntensity;

    public String toString() {
        return this.messageContent;
    }

}
