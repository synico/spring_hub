package com.ge.hc.healthlink.transform;

import com.ge.hc.healthlink.entity.PowerStatusMessage;
import com.ge.hc.healthlink.repository.PowerStatusMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;

public class PowerStatusMsgTransformer implements GenericTransformer<String, String> {

    @Autowired
    private PowerStatusMessageRepository powerStatusMessageRepository;

    @Override
    public String transform(String msg) {
        PowerStatusMessage msgEntity = new PowerStatusMessage();
        powerStatusMessageRepository.save(msgEntity.convertMsg2Entity(msg));
        return msg;
    }

}
