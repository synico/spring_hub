package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.ElectricityHeartbeat;
import com.ge.hc.healthlink.entity.HeartbeatKey;
import org.springframework.data.repository.CrudRepository;

public interface ElectricityHeartbeatRepository extends CrudRepository<ElectricityHeartbeat, HeartbeatKey> {
}
