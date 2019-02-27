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

import java.time.ZonedDateTime;
import java.util.LinkedList;

@Component
public class ElectricityHeartbeatTransformer implements GenericTransformer<String, String> {

    private static final Log LOGGER = LogFactory.getLog(ElectricityHeartbeatTransformer.class);

    @Autowired
    private ElectricityHeartbeatRepository heartbeatRepository;

    @Autowired
    private DeviceStatusCheckService deviceStatusCheckService;

    @Override
    public String transform(String source) {
        LOGGER.debug("Heartbeat message: " + source);
        LinkedList<ElectricityHeartbeat> msgEntities = null;
        if(validateMessage(source)) {
            String[] infos = source.split("\\|");
            String status = infos[0].trim();
            String assetMAC = infos[1].trim();

            Integer eventBeginDate = Integer.parseInt(infos[2].trim()) - 3 * 60 * 60;
            Long nowClientDate = ZonedDateTime.now().toEpochSecond();
            if(nowClientDate - eventBeginDate > 360) {
                LOGGER.info("### Synchronize begin date of event");
                eventBeginDate = nowClientDate.intValue() - infos.length;
            }

            msgEntities = new LinkedList<>();
            ElectricityHeartbeat heartbeat = null;
            HeartbeatKey heartbeatKey = null;

            for(int i = 3; i < infos.length; i++) {
                heartbeatKey = new HeartbeatKey();
                heartbeatKey.setAssetMAC(assetMAC.trim());
                heartbeatKey.setEventDate(eventBeginDate);
                heartbeat = new ElectricityHeartbeat();
                heartbeat.setHeartbeatKey(heartbeatKey);
                heartbeat.setStatus(status);
                eventBeginDate += 1;
                heartbeat.setElectricity(Integer.parseInt(infos[i].trim()));
                msgEntities.add(heartbeat);
            }

            heartbeatRepository.saveAll(msgEntities);
            deviceStatusCheckService.checkDeviceStatus(msgEntities);
        }
        return source;
    }

    private boolean validateMessage(String msg) {
        boolean isValid = true;
        if(StringUtils.isBlank(msg)) {
            isValid = false;
            LOGGER.debug("blank message");
        } else {
            String[] items = msg.split("\\|");
            if(items.length < 4) {
                isValid = false;
                LOGGER.debug("invalid message format, payload: " + msg);
            }
        }
        return isValid;
    }

}
