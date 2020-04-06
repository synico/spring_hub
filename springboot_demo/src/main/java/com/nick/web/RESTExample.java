package com.nick.web;

import com.nick.entity.OrgInfo;
import com.nick.entity.UserAccount;
import com.nick.service.ElectricityMessageService;
import com.nick.entity.ElectricityMessage;
import com.nick.service.OrgInfoService;
import com.nick.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RESTExample {

    private static final Logger log = LoggerFactory.getLogger(RESTExample.class);

    @Autowired
    private ElectricityMessageService electricityMessageService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private OrgInfoService orgInfoService;

    @RequestMapping(path = {"/rest/example", "/rest/demo"})
    String home() {
        if(false) {
            ElectricityMessage msg = new ElectricityMessage();
            electricityMessageService.saveElectricityMsg(msg.convertMsg2Entity("C|BAFC1234|1500000|5|4|3"));
        }

        if(false) {
            userAccountService.findUsers();
        }

        List<OrgInfo> orgInfoList = null;
        if(true) {
            orgInfoList = orgInfoService.findOrgByUid("951934d0515243ab993b958a0b41b72f");
            for(OrgInfo orgInfo : orgInfoList) {
                List<UserAccount> userAccounts = orgInfo.getUserAccounts();
                log.info("org name: " + orgInfo.getName() + ", num of users: " + userAccounts.size());
            }
        }

        Optional<OrgInfo> orgInfoOptional;
        if(false) {
            orgInfoOptional = orgInfoService.findOrgInfoByUid("951934d0515243ab993b958a0b41b72f");
            if(orgInfoOptional.isPresent()) {
                OrgInfo orgInfo = orgInfoOptional.get();
            }
        }

        return "Hello World";
    }
}
