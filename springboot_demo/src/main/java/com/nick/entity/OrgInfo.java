package com.nick.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nick.liu
 */
@Data
@Entity
@Table(name = "org_info")
public class OrgInfo {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "uid")
    private String uid;

    @Column(name = "name")
    private String name;

    @Column(name = "org_level")
    private Integer orgLevel;

    @Column(name = "org_type")
    private Integer orgType;

    @Column(name = "parent_uid")
    private String parentUid;

    @Column(name = "short_name")
    private String shortName;
}
