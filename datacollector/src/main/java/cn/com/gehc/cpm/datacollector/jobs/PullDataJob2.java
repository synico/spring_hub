package cn.com.gehc.cpm.datacollector.jobs;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PullDataJob2 {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss");

    public void pullData() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("pullData2: " + now.format(formatter));
    }

}
