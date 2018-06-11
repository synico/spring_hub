package com.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HystrixHelloService {
    
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "hystrixHelloFallback")
    public String hystrixHelloService() {
        return restTemplate.getForEntity("http://EUREKA-HELLO-SERVICE/hello", String.class).getBody();
    }
    
    public String hystrixHelloFallback() {
        return "hystrixHelloFallback";
    }

}
