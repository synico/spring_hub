package cn.com.gehc.cpm.datacollector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Configuration
//@ImportResource(locations = {"beans/*.xml"})
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean("defaultDS")
    @Primary
    public DataSource primaryDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("custom.datasource.primary.driverClassName"));
        dataSource.setUrl(env.getProperty("custom.datasource.primary.url"));
        dataSource.setUsername(env.getProperty("custom.datasource.primary.username"));
        dataSource.setPassword(env.getProperty("custom.datasource.primary.password"));
        return dataSource;
    }
}
