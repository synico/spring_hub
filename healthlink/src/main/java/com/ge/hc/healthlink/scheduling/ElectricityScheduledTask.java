package com.ge.hc.healthlink.scheduling;

import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.entity.ElectricityMsgAggregation;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ElectricityScheduledTask {

    private static final Log LOGGER = LogFactory.getLog(ElectricityScheduledTask.class);

    @Autowired
    private ElectricityMessageRepository electricityMessageRepository;

    @Scheduled(cron = "0 */1 * * * ?")
    public void mergeElectricityMsgByMinutes() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Long currentEpoch = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli()/1000;
        currentEpoch = currentEpoch + 3 * 60 * 60;
        List<ElectricityMessage> electricityMessages = electricityMessageRepository.findByEventDateBetween(currentEpoch.intValue() - 60, currentEpoch.intValue());

        Map<String, List<ElectricityMsgAggregation>> electricityMaps = new HashMap<>();
        for(ElectricityMessage electricityMessage : electricityMessages) {
            LOGGER.info("formatted event time: " + electricityMessage.getEventTimestamp());
            if(electricityMaps.containsKey(electricityMessage.getAssetMAC())) {
                List<ElectricityMsgAggregation> emaList = electricityMaps.get(electricityMessage.getAssetMAC());
            } else {
                ElectricityMsgAggregation ema = new ElectricityMsgAggregation();
//                ema.setAssetMAC(electricityMessage.getAssetMAC());
            }
        }
        LOGGER.info("Now local date time: " + currentEpoch + ", num of messages: " + electricityMessages.size());
    }
}
