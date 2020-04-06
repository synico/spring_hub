package com.nick.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyAppRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MyAppRunner.class);
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) {
        log.debug("run MyAppRunner..................");
    }
}
