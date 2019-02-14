package com.ge.hc.healthlink.service;

import com.ge.hc.healthlink.entity.Device;
import com.ge.hc.healthlink.entity.DeviceCategory;
import com.ge.hc.healthlink.entity.ElectricityHeartbeat;
import com.ge.hc.healthlink.repository.DeviceCategoryRepository;
import com.ge.hc.healthlink.repository.DeviceRepository;
import com.ge.hc.healthlink.repository.DeviceStatusRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceStatusCheckService {

    private static final Log LOGGER = LogFactory.getLog(DeviceStatusCheckService.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    private Device findDeviceByAssetMAC(String assetMAC) {
        LOGGER.debug("assetMAC: " + assetMAC);
        Device device = deviceRepository.findByIotId(assetMAC);
        LOGGER.info("assetMAC: " + assetMAC + ", deviceCategoryUid: " + (device != null ? device.getDeviceCategoryUuid() : "empty"));
        return device;
    }

    private DeviceCategory findCategoryByDevice(Device device) {
        DeviceCategory deviceCategory = null;
        if(device != null) {
            Optional<DeviceCategory> category = deviceCategoryRepository.findById(device.getDeviceCategoryUuid());
            deviceCategory = category.isPresent() ? category.get() : null;
        }
        return deviceCategory;
    }

    public void checkDeviceStatus(List<ElectricityHeartbeat> msgEntities) {
        if(msgEntities != null && msgEntities.size() > 0) {
            ElectricityHeartbeat heartbeat = msgEntities.get(0);
            String assetMAC = heartbeat.getHeartbeatKey().getAssetMAC();
            Device device = this.findDeviceByAssetMAC(assetMAC);
            if(device == null) {
                LOGGER.info("No device was found by MAC: " + assetMAC);
            } else {
                DeviceCategory category = this.findCategoryByDevice(device);
                if(category == null) {
                    LOGGER.info("No device category was found by device[MAC]: " + assetMAC);
                } else {
                    //Check device status
                    for(ElectricityHeartbeat hb : msgEntities) {
                        int value = hb.getElectricity();
                        int eventDate = hb.getHeartbeatKey().getEventDate();
                        LOGGER.debug("Electricity: " + value + ", Event Date: " + eventDate);
                    }
                }
            }
        }
    }

}
