package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "asset_mac", columnDefinition = "CHAR(16")
    private String assetMAC;

    @Column(name = "create_date")
    private Integer createDate;

    @Column(name = "electricity_value")
    private Integer electricity;

    @Column(name = "instant_power")
    private Integer instantPower;

    @Column(name = "field_intensity")
    private Integer fieldIntensity;

    @Column(name = "msg_topic", columnDefinition = "CHAR(32)")
    private String topic;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate = new Date();

    public String toString() {
        return this.topic + ":" + this.messageContent;
    }

}
