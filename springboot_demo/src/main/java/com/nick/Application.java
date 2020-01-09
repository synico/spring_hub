package com.nick;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nick.liu
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        Map<String, Object> defaultProps = new HashMap<>(16);
        defaultProps.put("spring.profiles.default", "dev");
        app.setDefaultProperties(defaultProps);

        /**
         * TEST
         */
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = (classLoader != null) ?
                    classLoader.getResources(SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION);
            while(urls.hasMoreElements()) {
                URL url = urls.nextElement();
                log.info("URL: {}", url.getContent());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        app.run(args);
    }

}
