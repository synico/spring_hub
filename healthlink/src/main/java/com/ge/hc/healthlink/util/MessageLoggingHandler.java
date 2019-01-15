package com.ge.hc.healthlink.util;

import org.springframework.integration.handler.LoggingHandler;

public class MessageLoggingHandler {

    public LoggingHandler logger(LoggingHandler.Level level, String loggerName) {
        LoggingHandler loggingHandler = new LoggingHandler(level);
        loggingHandler.setLoggerName(loggerName);
        return loggingHandler;
    }

}