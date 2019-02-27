package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "hl_link_message")
@Data
public class LinkMessage {

    @EmbeddedId
    private HeartbeatKey heartbeatKey;

    @Column(name = "app_version", columnDefinition = "VARCHAR(8)")
    private String appVersion;

    @Column(name = "asset_ip_address", columnDefinition = "VARCHAR(16)")
    private String assetIP;

    @Column(name = "ap_ssid_name", columnDefinition = "VARCHAR(64)")
    private String apSSID;

    @Column(name = "ap_mac", columnDefinition = "VARCHAR(20)")
    private String apMAC;

    public LinkMessage convertMsg2Entity(String msg) {
        String infos[] = msg.split("\\|");
        if(infos.length == 7) {
            HeartbeatKey heartbeatKey = new HeartbeatKey();
            heartbeatKey.setAssetMAC(infos[1]);
            heartbeatKey.setEventDate(Integer.parseInt(infos[4]) - 3 * 60 * 60);
            this.setHeartbeatKey(heartbeatKey);
            this.setAppVersion(infos[2]);
            this.setAssetIP(infos[3]);
            this.setApSSID(infos[5]);
            this.setApMAC(infos[6].replace(":", ""));
        }
        return this;
    }

}
