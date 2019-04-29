package cn.com.gehc.cpm.datacollector.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PullDataJob2 {

    private static final Logger log = LoggerFactory.getLogger(PullDataJob2.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss");

    public void pullData() {
        LocalDateTime now = LocalDateTime.now();
        log.info("pullData2 date: {}", now.format(formatter));
    }

}
