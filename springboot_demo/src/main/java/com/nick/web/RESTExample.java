package com.nick.web;

import com.nick.service.ElectricityMessageService;
import com.nick.entity.ElectricityMessage;
import com.nick.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTExample {

    @Autowired
    private ElectricityMessageService electricityMessageService;

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(path = {"/rest/example", "/rest/demo"})
    String home() {
        ElectricityMessage msg = new ElectricityMessage();
        electricityMessageService.saveElectricityMsg(msg.convertMsg2Entity("C|BAFC1234|1500000|5|4|3"));

        userAccountService.findUsers();

        return "Hello World";
    }
}
