package com.nick.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTExample {

    @RequestMapping(path = {"/rest/example", "/rest/demo"})
    String home() {
        return "Hello World";
    }
}
