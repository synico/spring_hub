package cn.com.gehc.cpm.datacollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DatacollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatacollectorApplication.class, args);
    }

}
