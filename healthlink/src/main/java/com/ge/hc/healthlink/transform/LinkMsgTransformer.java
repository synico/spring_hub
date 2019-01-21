package com.ge.hc.healthlink.transform;

import com.ge.hc.healthlink.entity.LinkMessage;
import com.ge.hc.healthlink.repository.LinkMessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class LinkMsgTransformer implements GenericTransformer<String, String> {

    private static final Log LOGGER = LogFactory.getLog(LinkMsgTransformer.class);

    @Autowired
    private LinkMessageRepository linkMessageRepository;

    @Override
    public String transform(String msg) {
        LOGGER.debug("msg: " + msg);
        LinkMessage msgEntity = new LinkMessage();
        linkMessageRepository.save(msgEntity.convertMsg2Entity(msg));
        return msg;
    }
}
