package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

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

    @Column(name = "event_date")
    private Integer eventDate;

    @Column(name = "electricity_value")
    private Integer electricity;

    @Column(name = "instant_power")
    private Integer instantPower;

    @Column(name = "field_intensity")
    private Integer fieldIntensity;

    @Transient
    private String eventTimestamp;

    public String getEventTimestamp() {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(this.eventDate.longValue(), 0, ZoneOffset.of("+8"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return localDateTime.format(dateTimeFormatter);
    }

    public ElectricityMessage convertMsg2Entity(String theMsg) {
        String infos [] = theMsg.split("\\|");
        if(infos.length == 6) {
            this.setStatus(infos[0]);
            this.setAssetMAC(infos[1].trim());
            this.setEventDate(Integer.parseInt(infos[2].trim()) - 3 * 60 * 60);
            this.setElectricity(Integer.parseInt(infos[3].trim()));
            this.setInstantPower(Integer.parseInt(infos[4]));
            this.setFieldIntensity(Integer.parseInt(infos[5]));
        }
        return this;
    }

}
