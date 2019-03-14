package com.nick.service;

import com.nick.repository.ElectricityMessageRepository;
import com.nick.entity.ElectricityMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElectricityMessageService {

    private static final Logger log = LoggerFactory.getLogger(ElectricityMessageService.class);

    @Autowired
    private ElectricityMessageRepository electricityMessageRepository;

    public void saveElectricityMsg(ElectricityMessage msg) {
        ElectricityMessage rst = electricityMessageRepository.save(msg);
        log.info("result: " + rst.toString());
    }

}
