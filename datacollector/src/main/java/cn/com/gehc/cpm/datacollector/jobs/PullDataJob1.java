package cn.com.gehc.cpm.datacollector.jobs;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class PullDataJob1 {

    private static final Logger log = LoggerFactory.getLogger(PullDataJob1.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss");

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

    public void printRow(@Headers Map<String, Object> headers, @Body Object body) {
        List<Map<String, List<String>>> studys = (List<Map<String, List<String>>>) body;
        System.out.println("stets");
    }

}
