package com.nick.service;

import com.nick.repository.OrgInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author nick.liu
 * @create 2019/3/14 15:20
 */
@Component
public class OrgInfoService {

    private static final Logger log = LoggerFactory.getLogger(OrgInfoService.class);

    @Autowired
    private OrgInfoRepository orgInfoRepository;

}
