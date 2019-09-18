package cn.com.gehc.cpm.datacollector.jobs;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class PullDataJob1 {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss");

    public void pullData() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now: " + now.format(formatter));
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        now = LocalDateTime.now();
        System.out.println("pullData1: " + now.format(formatter));
    }

    public void printRow(@Headers Map<String, Object> headers, @Body Object body) {
        List<Map<String, Object>> studys = (List<Map<String, Object>>) body;
        System.out.println("stets");
    }

}
