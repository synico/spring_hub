package com.ge.hc.healthlink.transform;

import com.ge.hc.healthlink.entity.PowerStatusMessage;
import com.ge.hc.healthlink.repository.PowerStatusMessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class PowerStatusMsgTransformer implements GenericTransformer<String, String> {

    private static final Log LOGGER = LogFactory.getLog(PowerStatusMsgTransformer.class);

    @Autowired
    private PowerStatusMessageRepository powerStatusMessageRepository;

    @Override
    public String transform(String msg) {
        LOGGER.debug("msg: " + msg);
        PowerStatusMessage msgEntity = new PowerStatusMessage();
        powerStatusMessageRepository.save(msgEntity.convertMsg2Entity(msg));
        return msg;
    }

}
