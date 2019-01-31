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

    @Column(name = "power_on_date")
    private Integer powerOnDate;

    @Column(name = "power_off_date")
    private Integer powerOffDate;

    public PowerStatusMessage convertMsg2Entity(String msg) {
        String infos[] = msg.split("\\|");
        if(infos.length == 5) {
            this.setAssetMAC(infos[1]);
            this.setEventSeq(Integer.parseInt(infos[2]));
            this.setPowerOnDate(Integer.parseInt(infos[3]) - 3 * 60 * 60);
            this.setPowerOffDate(Integer.parseInt(infos[4]) - 3 * 60 * 60);
        }
        return this;
    }

}
