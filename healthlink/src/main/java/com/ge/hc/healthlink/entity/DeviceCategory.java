package com.ge.hc.healthlink.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "hl_device_category")
public class DeviceCategory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;

    private String name;

    private String deviceModel;

    private String vendor;

    private String registrationNum;

    private Double powerOnElectricCurrentStart;

    private Double powerOnElectricCurrentEnd;

    private Double powerOffElectricCurrentStart;

    private Double powerOffElectricCurrentEnd;

    private Double standByElectricCurrentStart;

    private Double standByElectricCurrentEnd;

    private Double inUseElectricCurrentStart;

    private Double inUseElectricCurrentEnd;

}
