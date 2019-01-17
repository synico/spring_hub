package com.ge.hc.healthlink.transform;

import com.ge.hc.healthlink.entity.LinkMessage;
import com.ge.hc.healthlink.repository.LinkMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;

public class LinkMsgTransformer implements GenericTransformer<String, String> {

    @Autowired
    private LinkMessageRepository linkMessageRepository;

    @Override
    public String transform(String msg) {
        LinkMessage msgEntity = new LinkMessage();
        linkMessageRepository.save(msgEntity.convertMsg2Entity(msg));
        return msg;
    }
}
