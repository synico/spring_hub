package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.DeviceStatus;
import com.ge.hc.healthlink.entity.HeartbeatKey;
import org.springframework.data.repository.CrudRepository;

public interface DeviceStatusRepository extends CrudRepository<DeviceStatus, HeartbeatKey> {
}
