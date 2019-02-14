package com.ge.hc.healthlink.transform;

import com.ge.hc.healthlink.entity.ElectricityHeartbeat;
import com.ge.hc.healthlink.entity.HeartbeatKey;
import com.ge.hc.healthlink.repository.ElectricityHeartbeatRepository;
import com.ge.hc.healthlink.service.DeviceStatusCheckService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ElectricityHeartbeatTransformer implements GenericTransformer<String, String> {

    private static final Log LOGGER = LogFactory.getLog(ElectricityHeartbeatTransformer.class);

    @Autowired
    private ElectricityHeartbeatRepository heartbeatRepository;

    @Autowired
    private DeviceStatusCheckService deviceStatusCheckService;

    @Override
    public String transform(String source) {
        LOGGER.debug("msg: " + source);
        List<ElectricityHeartbeat> msgEntities = null;
        if(StringUtils.isNotBlank(source)) {
            String infos[] = source.split("\\|");
            String status = infos[0].trim();
            String assetMAC = infos[1].trim();
            Integer eventBeginDate = Integer.parseInt(infos[2].trim()) - 3 * 60 * 60;
            msgEntities = new ArrayList<>(infos.length - 3);
            ElectricityHeartbeat heartbeat = null;
            HeartbeatKey heartbeatKey = null;
            for(int i = 3; i < infos.length; i++) {
                heartbeatKey = new HeartbeatKey();
                heartbeatKey.setAssetMAC(assetMAC);
                heartbeatKey.setEventDate(eventBeginDate);
                heartbeat = new ElectricityHeartbeat();
                heartbeat.setHeartbeatKey(heartbeatKey);
                heartbeat.setStatus(status);
//                heartbeat.setAssetMAC(assetMAC);
//                heartbeat.setEventDate(eventBeginDate);
                eventBeginDate += 1;
                heartbeat.setElectricity(Integer.parseInt(infos[i].trim()));
                msgEntities.add(heartbeat);
            }
            if(infos.length != 23 || msgEntities.size() != 20) {
                LOGGER.info("ERROR: " + source);
            }
            heartbeatRepository.saveAll(msgEntities);
            deviceStatusCheckService.checkDeviceStatus(msgEntities);
        }
        return source;
    }
}
