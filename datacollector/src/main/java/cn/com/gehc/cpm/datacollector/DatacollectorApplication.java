package cn.com.gehc.cpm.datacollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ImportResource(locations = {"dosewatch-datasource.xml"})
public class DatacollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatacollectorApplication.class, args);
    }

}
