package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, String> {

    Device findByIotId(String iotId);

}
