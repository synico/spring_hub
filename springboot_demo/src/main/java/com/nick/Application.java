package com.nick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        Map<String, Object> defaultProps = new HashMap<>();
        defaultProps.put("spring.profiles.default", "dev");
        app.setDefaultProperties(defaultProps);
        app.run(args);
    }

}
