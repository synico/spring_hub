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

    @Column(name = "message_content", columnDefinition = "CHAR(255)")
    private String messageContent;
}
