package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "hl_link_message")
@Data
public class LinkMessage {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "message_id", columnDefinition = "CHAR(32)")
    private String id;

    @Column(name = "asset_mac", columnDefinition = "CHAR(16)")
    private String assetMAC;

    @Column(name = "app_version", columnDefinition = "CHAR(8)")
    private String appVersion;

    @Column(name = "asset_ip_address", columnDefinition = "CHAR(16)")
    private String assetIP;

    @Column(name = "event_date", columnDefinition = "CHAR(12)")
    private String eventDate;

    @Column(name = "ap_ssid_name", columnDefinition = "VARCHAR(64)")
    private String apSSID;

    @Column(name = "ap_mac", columnDefinition = "CHAR(20)")
    private String apMAC;

    public LinkMessage convertMsg2Entity(String msg) {
        String infos[] = msg.split("\\|");
        if(infos.length == 7) {
            this.setApMAC(infos[1]);
            this.setAppVersion(infos[2]);
            this.setAssetIP(infos[3]);
            this.setEventDate(infos[4]);
            this.setApSSID(infos[5]);
            this.setApMAC(infos[6]);
        }
        return this;
    }

}
