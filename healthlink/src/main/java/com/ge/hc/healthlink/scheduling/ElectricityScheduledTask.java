package com.ge.hc.healthlink.scheduling;

import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.entity.ElectricityMsgAggregation;
import com.ge.hc.healthlink.entity.LinkMessage;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import com.ge.hc.healthlink.repository.ElectricityMsgAggregationRepository;
import com.ge.hc.healthlink.repository.LinkMessageRepository;
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

    @Autowired
    private ElectricityMsgAggregationRepository electricityMsgAggregationRepository;

    @Autowired
    private LinkMessageRepository linkMessageRepository;

    @Scheduled(cron = "0 */1 * * * ?")
    public void mergeElectricityMsgByMinutes() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Long currentEpoch = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli()/1000;
//        currentEpoch = currentEpoch + 3 * 60 * 60;
        List<ElectricityMessage> electricityMessages = electricityMessageRepository.findByEventDateBetween(currentEpoch.intValue() - 60, currentEpoch.intValue());

        Map<String, ElectricityMsgAggregation> emaMap = new HashMap<>();
        String emaMapKey = null;
        for(ElectricityMessage msg : electricityMessages) {
            LOGGER.debug("formatted event time: " + msg.getEventTimestamp());
            ElectricityMsgAggregation ema = null;
            emaMapKey = msg.getAssetMAC() + "|" + msg.getEventTimestamp();
            if(emaMap.containsKey(emaMapKey)) {
                ema = emaMap.get(emaMapKey);
                ema.setTotalElectricity(ema.getTotalElectricity() + msg.getElectricity());
            } else {
                ema = new ElectricityMsgAggregation();
                ema.setAssetMAC(msg.getAssetMAC());
                ema.setEventDate(msg.getEventTimestamp());
                ema.setTotalElectricity(msg.getElectricity());
                emaMap.put(emaMapKey, ema);
            }
        }
        for(Map.Entry<String, ElectricityMsgAggregation> entry : emaMap.entrySet()) {
            ElectricityMsgAggregation ema = entry.getValue();
            LinkMessage linkMessage = linkMessageRepository.findApMACByAssetMAC(ema.getAssetMAC());
            if(linkMessage != null) {
                ema.setApMAC(linkMessage.getApMAC().trim());
            }
            electricityMsgAggregationRepository.save(ema);
        }
        LOGGER.info("Now local date time: " + currentEpoch + ", num of messages: " + electricityMessages.size());
    }
}
