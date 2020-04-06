package com.nick.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyApplicationListener<E extends ApplicationEvent> implements ApplicationListener {

    private static final Logger log = LoggerFactory.getLogger(MyApplicationListener.class);

    public MyApplicationListener() {
        log.debug("constructor of MyApplicationListener");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.debug("Event name: " + event.getClass().getName());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        log.debug("##########################################################");
        if(event instanceof ApplicationStartingEvent) {
            log.debug("Event {} is invoked @ {}", ApplicationStartingEvent.class.getName(), dtf.format(LocalDateTime.now()));
        }
        if(event instanceof ApplicationEnvironmentPreparedEvent) {
            log.debug("Event {} is invoked @ {}", ApplicationEnvironmentPreparedEvent.class.getName(), dtf.format(LocalDateTime.now()));
        }
        if(event instanceof ApplicationPreparedEvent) {
            log.debug("Event {} is invoked @ {}", ApplicationPreparedEvent.class.getName(), dtf.format(LocalDateTime.now()));
        }
        if(event instanceof ApplicationReadyEvent) {
            log.debug("Event {} is invoked @ {}", ApplicationReadyEvent.class.getName(), dtf.format(LocalDateTime.now()));
        }
        if(event instanceof ApplicationFailedEvent) {
            log.debug("Event {} is invoked @ {}", ApplicationFailedEvent.class.getName(), dtf.format(LocalDateTime.now()));
        }
        log.debug("##########################################################");
    }
}
