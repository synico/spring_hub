package com.nick.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author nick.liu
 */
@Data
@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "hospital_id")
    private Integer hospitalId;

    @Column(name = "hospital_uid")
    private String hospitalUid;

    @Column(name = "institution_uid")
    private String institutionUid;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "is_site_admin")
    private Boolean isSiteAdmin;

    @Column(name = "last_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

}
