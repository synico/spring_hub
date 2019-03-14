package com.nick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nick.liu
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        Map<String, Object> defaultProps = new HashMap<>(16);
        defaultProps.put("spring.profiles.default", "dev");
        app.setDefaultProperties(defaultProps);
        app.run(args);
    }

}
