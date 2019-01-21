package com.ge.hc.healthlink.transform;

import com.ge.hc.healthlink.entity.ElectricityMessage;
import com.ge.hc.healthlink.repository.ElectricityMessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class ElectricityMsgTransformer implements GenericTransformer<String, String> {

    private static final Log LOGGER = LogFactory.getLog(ElectricityMsgTransformer.class);

    @Autowired
    private ElectricityMessageRepository electricityMessageRepository;

    @Override
    public String transform(String msg) {
        LOGGER.debug("msg: " + msg);
        ElectricityMessage msgEntity = new ElectricityMessage();
        electricityMessageRepository.save(msgEntity.convertMsg2Entity(msg));
        return msg;
    }

}
