package com.ge.hc.healthlink.service;

import com.ge.hc.healthlink.entity.*;
import com.ge.hc.healthlink.repository.DeviceCategoryRepository;
import com.ge.hc.healthlink.repository.DeviceRepository;
import com.ge.hc.healthlink.repository.DeviceStatusRepository;
import com.ge.hc.healthlink.repository.ElectricityHeartbeatRepository;
import com.ge.hc.healthlink.util.DeviceStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeviceStatusCheckService {

    private static final Log LOGGER = LogFactory.getLog(DeviceStatusCheckService.class);

    /**
     * key: assetMAC
     * value: ElectricityHeartbeat
     */
    private Map<String, ElectricityHeartbeat> lastHeartbeat = new ConcurrentHashMap<>();

    /**
     * key: assetMAC
     * value: deviceStatusEnum
     */
    private Map<String, DeviceStatus> latestDeviceStatus = new ConcurrentHashMap<>();

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    @Autowired
    private ElectricityHeartbeatRepository heartbeatRepository;

    private Device findDeviceByAssetMAC(String assetMAC) {
        LOGGER.debug("-asset_mac: " + assetMAC);
        Device device = deviceRepository.findByIotId(assetMAC);
        LOGGER.info("-asset_mac: " + assetMAC + ", device_category_uid: " + (device != null ? device.getDeviceCategoryUuid() : "empty"));
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

    private ElectricityHeartbeat getPreviousHeartbeat(String assetMAC) {
        // First, retrieve heartbeat from cache(ConcurrentHashMap)
        ElectricityHeartbeat previousHeartbeat = lastHeartbeat.get(assetMAC);
        if(previousHeartbeat == null) {
            // Since heartbeat info hasn't been added to cache, so retrieve it from database
            previousHeartbeat = heartbeatRepository.findLatestHeartbeatByAssetMAC(assetMAC);
        }
        return previousHeartbeat;
    }

    private DeviceStatus getPreviousDeviceStatus(String assetMAC) {
        DeviceStatus previousStatus = latestDeviceStatus.get(assetMAC);
        if(previousStatus == null) {
            previousStatus = deviceStatusRepository.findLatestDeviceStatusByAssetMAC(assetMAC);
            if(previousStatus == null) {
                previousStatus = new DeviceStatus();
                previousStatus.setStatus(Integer.toString(DeviceStatusEnum.UNKNOWN.getStatusCode()));
            }
        }
        return previousStatus;
    }

    private DeviceStatusEnum updateDeviceStatus(DeviceStatusEnum toStatus, HeartbeatKey hbKey) {
        LOGGER.info("* UPDATE DEVICE [" + hbKey.getAssetMAC() + "] STATUS TO: " + toStatus.name());
        DeviceStatus status = new DeviceStatus();
        status.setHeartbeatKey(hbKey);
        status.setStatus(Integer.toString(toStatus.getStatusCode()));
        deviceStatusRepository.save(status);
        latestDeviceStatus.put(hbKey.getAssetMAC(), status);
        return toStatus;
    }

    private boolean isDeviceStandby(DeviceCategory category, ElectricityHeartbeat hb) {
        if(hb.getElectricity() > category.getStandByElectricCurrentStart() &&
            hb.getElectricity() < category.getStandByElectricCurrentEnd()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDeviceRunning(DeviceCategory category, ElectricityHeartbeat hb) {
        if(hb.getElectricity() > category.getInUseElectricCurrentStart() &&
            hb.getElectricity() < category.getInUseElectricCurrentEnd()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDevicePowerOn(DeviceCategory category, ElectricityHeartbeat previousHeartbeat, ElectricityHeartbeat currentHeartbeat) {
        int diff = currentHeartbeat.getElectricity() - previousHeartbeat.getElectricity();
        if(diff >= category.getPowerOnElectricCurrentStart()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDevicePowerOff(DeviceCategory category, LinkedList<ElectricityHeartbeat> msgEntities, int index) {
        ElectricityHeartbeat current = msgEntities.get(index);
        if(current.getElectricity() < category.getPowerOffElectricCurrentEnd()) {
            return true;
        } else {
            ElectricityHeartbeat headHeartbeat = msgEntities.get(0);
            int secondDiff = current.getHeartbeatKey().getEventDate() - headHeartbeat.getHeartbeatKey().getEventDate();
            if(index == 1 && secondDiff > 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void checkDeviceStatus(LinkedList<ElectricityHeartbeat> msgEntities) {
        if(msgEntities != null && msgEntities.size() > 0) {
            ElectricityHeartbeat heartbeat = msgEntities.getFirst();
            String assetMAC = heartbeat.getHeartbeatKey().getAssetMAC();
            Device device = this.findDeviceByAssetMAC(assetMAC);
            if(device == null) {
                LOGGER.debug("no device was found by MAC: " + assetMAC);
            } else {
                DeviceCategory category = this.findCategoryByDevice(device);
                if(category == null) {
                    LOGGER.debug("no device category was found by device[MAC]: " + assetMAC);
                } else {
                    LOGGER.debug("device category is found by device[MAC]: " + assetMAC);
                    // Retrieve heartbeat from previous message
                    ElectricityHeartbeat previousHeartbeat = this.getPreviousHeartbeat(assetMAC);
                    if(previousHeartbeat != null) {
                        lastHeartbeat.put(assetMAC, previousHeartbeat);
                        msgEntities.addFirst(previousHeartbeat);
                    }

                    // Retrieve device status
                    DeviceStatus deviceStatus = this.getPreviousDeviceStatus(assetMAC);

                    //START
                    ElectricityHeartbeat head = msgEntities.get(0);
                    ElectricityHeartbeat current = null;
                    DeviceStatusEnum previousStatus = null;
                    if(StringUtils.isNotEmpty(deviceStatus.getStatus())) {
                        previousStatus = DeviceStatusEnum.getStatusByCode(Integer.parseInt(deviceStatus.getStatus().trim()));
                    }
                    for(int idx = 1; idx < msgEntities.size(); idx++) {
                        current = msgEntities.get(idx);
                        switch (previousStatus) {
                            case POWERON:
                                // result: STANDBY || RUNNING
                                if(isDeviceStandby(category, current)) {
                                    // update device status to STANDBY
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.STANDBY, current.getHeartbeatKey());
                                } else if(isDeviceRunning(category, current)) {
                                    // update device status to RUNNING
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.RUNNING, current.getHeartbeatKey());
                                }
                                break;
                            case STANDBY:
                                // result: RUNNING || POWEROFF
                                if(isDeviceRunning(category, current)) {
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.RUNNING, current.getHeartbeatKey());
                                } else if(isDevicePowerOff(category, msgEntities, idx)) {
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.POWEROFF, current.getHeartbeatKey());
                                }
                                break;
                            case RUNNING:
                                // result: STANDBY || POWEROFF
                                if(isDeviceStandby(category, current)) {
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.STANDBY, current.getHeartbeatKey());
                                } else if(isDevicePowerOff(category, msgEntities, idx)) {
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.POWEROFF, current.getHeartbeatKey());
                                }
                                break;
                            case POWEROFF:
                                // result: POWERON
                                if(isDevicePowerOn(category, head, current)) {
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.POWERON, current.getHeartbeatKey());
                                }
                                break;
                            default:
                                // result: POWERON
                                if(isDevicePowerOn(category, head, current)) {
                                    previousStatus = updateDeviceStatus(DeviceStatusEnum.POWERON, current.getHeartbeatKey());
                                }
                        }
                        head = current;
                    }
                    //END
                    lastHeartbeat.put(assetMAC, msgEntities.getLast());
                }
            }
        }
    }

}
