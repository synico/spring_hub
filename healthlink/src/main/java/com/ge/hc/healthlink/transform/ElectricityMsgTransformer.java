package com.ge.hc.healthlink.transform;

import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;

public class ElectricityMsgTransformer implements GenericTransformer<String, String> {

    @Autowired
    private ElectricityMessageRepository electricityMessageRepository;

    @Override
    public String transform(String msg) {
        ElectricityMessage msgEntity = new ElectricityMessage();
        electricityMessageRepository.save(msgEntity.convertMsg2Entity(msg));
        return msg;
    }

}
