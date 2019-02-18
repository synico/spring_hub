package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.ElectricityHeartbeat;
import com.ge.hc.healthlink.entity.HeartbeatKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ElectricityHeartbeatRepository extends CrudRepository<ElectricityHeartbeat, HeartbeatKey> {

    @Query(value = "select * from hl_electricity_heartbeat where asset_mac=?1 order by event_date desc limit 1", nativeQuery = true)
    ElectricityHeartbeat findLatestHeartbeatByAssetMAC(String assetMAC);

}
