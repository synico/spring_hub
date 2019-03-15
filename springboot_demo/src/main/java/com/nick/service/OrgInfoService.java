package com.nick.service;

import com.nick.entity.OrgInfo;
import com.nick.repository.OrgInfoRepository;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author nick.liu
 * @create 2019/3/14 15:20
 */
@Component
public class OrgInfoService {

    private static final Logger log = LoggerFactory.getLogger(OrgInfoService.class);

    @Autowired
    private OrgInfoRepository orgInfoRepository;

    public List<OrgInfo> findOrgByUid(String uid) {
        List<OrgInfo> orgInfoList = orgInfoRepository.findByUid(uid);
        return orgInfoList;
    }

    public Optional<OrgInfo> findOrgInfoByUid(String uid) {
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setUid(uid);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIncludeNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.ENDING);

        Example<OrgInfo> example = Example.of(orgInfo, matcher);

        Optional<OrgInfo> result = orgInfoRepository.findOne(example);
        return result;
    }

}
