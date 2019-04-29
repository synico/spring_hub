package cn.com.gehc.cpm.datacollector.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PullDataJob1 {

    private static final Logger log = LoggerFactory.getLogger(PullDataJob1.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss");

    public void pullData() {
        LocalDateTime now = LocalDateTime.now();
        log.info("pullData1 start date: {}", now.format(formatter));
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        now = LocalDateTime.now();
        log.info("pullData1 end date: {}", now.format(formatter));
    }

}
