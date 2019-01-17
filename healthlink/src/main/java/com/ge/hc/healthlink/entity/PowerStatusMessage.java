package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "hl_power_status_message")
@Data
public class PowerStatusMessage {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "message_id", columnDefinition = "CHAR(32)")
    private String id;

    @Column(name = "asset_mac", columnDefinition = "CHAR(16)")
    private String assetMAC;

    @Column(name = "event_seq")
    private Integer eventSeq;

    @Column(name = "power_on_date", columnDefinition = "CHAR(12)")
    private String powerOnDate;

    @Column(name = "power_off_date", columnDefinition = "CHAR(12)")
    private String powerOffDate;

    public PowerStatusMessage convertMsg2Entity(String msg) {
        String infos[] = msg.split("|");
        if(infos.length == 5) {
            this.setAssetMAC(infos[1]);
            this.setEventSeq(Integer.parseInt(infos[2]));
            this.setPowerOnDate(infos[3]);
            this.setPowerOffDate(infos[4]);
        }
        return this;
    }

}
