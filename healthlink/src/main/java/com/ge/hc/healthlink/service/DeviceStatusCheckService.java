package com.ge.hc.healthlink.service;

import com.ge.hc.healthlink.entity.*;
import com.ge.hc.healthlink.repository.DeviceCategoryRepository;
import com.ge.hc.healthlink.repository.DeviceRepository;
import com.ge.hc.healthlink.repository.DeviceStatusRepository;
import com.ge.hc.healthlink.repository.ElectricityHeartbeatRepository;
import com.ge.hc.healthlink.util.DeviceStatusEnum;
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
                previousStatus.setStatus("");
            }
        }
        return previousStatus;
    }

    public void checkDeviceStatus(LinkedList<ElectricityHeartbeat> msgEntities) {
        if(msgEntities != null && msgEntities.size() > 0) {
            ElectricityHeartbeat heartbeat = msgEntities.getFirst();
            String assetMAC = heartbeat.getHeartbeatKey().getAssetMAC();
            Device device = this.findDeviceByAssetMAC(assetMAC);
            if(device == null) {
                LOGGER.info("No device was found by MAC: " + assetMAC);
            } else {
                DeviceCategory category = this.findCategoryByDevice(device);
                if(category == null) {
                    LOGGER.info("No device category was found by device[MAC]: " + assetMAC);
                } else {
                    // Retrieve heartbeat from previous message
                    ElectricityHeartbeat previousHeartbeat = this.getPreviousHeartbeat(assetMAC);
                    if(previousHeartbeat != null) {
                        lastHeartbeat.put(assetMAC, previousHeartbeat);
                        msgEntities.addFirst(previousHeartbeat);
                    }

                    // Retrieve device status
                    DeviceStatus deviceStatus = this.getPreviousDeviceStatus(assetMAC);

                    //TODO
                    ElectricityHeartbeat head = msgEntities.get(0);
                    ElectricityHeartbeat current = null;
                    for(int idx = 1; idx < msgEntities.size(); idx++) {
                        current = msgEntities.get(idx);
                        // Step1. check if this is a power on event
                        int diff = current.getElectricity() - head.getElectricity();
                        if(diff > category.getPowerOnElectricCurrentStart()) {
                            // 前后两秒电流值的差值大于定义的开机电流变化值，如果之前设备状态为开机，则不更新设备状态
                            if(deviceStatus.getStatus().equals(DeviceStatusEnum.POWERON.getStatusCode())) {
                                // do nothing
                            } else {
                                // 更新状态
                                DeviceStatus status = new DeviceStatus();
                                status.setHeartbeatKey(current.getHeartbeatKey());
                                status.setStatus(Integer.toString(DeviceStatusEnum.POWERON.getStatusCode()));
                                deviceStatusRepository.save(status);
                                latestDeviceStatus.put(assetMAC, status);
                            }
                        }
                        // Step2. check if this is standby event
                        if(deviceStatus.getStatus().equals(DeviceStatusEnum.STANDBY.getStatusCode())) {
                            // do nothing
                        } else {
                            if(category.getStandByElectricCurrentStart() < current.getElectricity() &&
                                    category.getStandByElectricCurrentEnd() > current.getElectricity()) {
                                DeviceStatus status = new DeviceStatus();
                                status.setHeartbeatKey(current.getHeartbeatKey());
                                status.setStatus(Integer.toString(DeviceStatusEnum.STANDBY.getStatusCode()));
                                deviceStatusRepository.save(status);
                                latestDeviceStatus.put(assetMAC, status);
                            }
                        }

                        // Step3. check if this is running event
                        if(deviceStatus.getStatus().equals(DeviceStatusEnum.RUNNING.getStatusCode())) {
                            // do nothing
                        } else {
                            if(category.getInUseElectricCurrentStart() < current.getElectricity() &&
                                    category.getInUseElectricCurrentEnd() > current.getElectricity()) {
                                DeviceStatus status = new DeviceStatus();
                                status.setHeartbeatKey(current.getHeartbeatKey());
                                status.setStatus(Integer.toString(DeviceStatusEnum.RUNNING.getStatusCode()));
                                deviceStatusRepository.save(status);
                                latestDeviceStatus.put(assetMAC, status);
                            }
                        }

                        // Step4. check if this is a power off event
                        if(deviceStatus.getStatus().equals(DeviceStatusEnum.POWEROFF.getStatusCode())) {
                            // do nothing
                        } else {
                            if(category.getPowerOffElectricCurrentStart() < current.getElectricity() &&
                                    category.getPowerOffElectricCurrentEnd() > current.getElectricity()) {
                                DeviceStatus status = new DeviceStatus();
                                status.setHeartbeatKey(current.getHeartbeatKey());
                                status.setStatus(Integer.toString(DeviceStatusEnum.POWEROFF.getStatusCode()));
                                deviceStatusRepository.save(status);
                                latestDeviceStatus.put(assetMAC, status);
                            }
                        }
                        // Last step
                        head = current;
                    }
                    //END
                    lastHeartbeat.put(assetMAC, msgEntities.getLast());
                }
            }
        }
    }

}
