package com.ge.hc.healthlink.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class HeartbeatKey implements Serializable {

    @Column(name = "asset_mac", columnDefinition = "VARCHAR(16)")
    private String assetMAC;

    @Column(name = "event_date")
    private Integer eventDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartbeatKey that = (HeartbeatKey) o;
        return assetMAC.equals(that.assetMAC) &&
                eventDate.equals(that.eventDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetMAC, eventDate);
    }
}
