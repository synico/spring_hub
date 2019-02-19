package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.DeviceStatus;
import com.ge.hc.healthlink.entity.HeartbeatKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DeviceStatusRepository extends CrudRepository<DeviceStatus, HeartbeatKey> {

    @Query(value = "select * from hl_device_status where asset_mac=?1 and " +
            "event_date=(select max(event_date) from hl_device_status where asset_mac=?1)", nativeQuery = true)
    DeviceStatus findLatestDeviceStatusByAssetMAC(String assetMAC);

}
