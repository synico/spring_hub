package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "hl_electricity_message")
@Data
public class ElectricityMessage {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "message_id", columnDefinition = "CHAR(32)")
    private String id;

    @Column(name = "status", columnDefinition = "CHAR(2)")
    private String status;

    @Column(name = "asset_mac", columnDefinition = "CHAR(16)")
    private String assetMAC;

    @Column(name = "event_date", columnDefinition = "CHAR(12)")
    private String eventDate;

    @Column(name = "electricity_value")
    private Integer electricity;

    @Column(name = "instant_power")
    private Integer instantPower;

    @Column(name = "field_intensity")
    private Integer fieldIntensity;

    public ElectricityMessage convertMsg2Entity(String theMsg) {
        String infos [] = theMsg.split("\\|");
        if(infos.length == 6) {
            this.setStatus(infos[0]);
            this.setAssetMAC(infos[1]);
            this.setEventDate(infos[2]);
            this.setElectricity(Integer.parseInt(infos[3]));
            this.setInstantPower(Integer.parseInt(infos[4]));
            this.setFieldIntensity(Integer.parseInt(infos[5]));
        }
        return this;
    }

}
