package com.nick.jpa.dev;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

//@Configuration
//@EnableJpaRepositories(entityManagerFactoryRef = "devEntityManagerFactory", transactionManagerRef = "devTransactionManager")
//@EnableTransactionManagement
public class DevDBConfig {

//    @Bean
    public LocalContainerEntityManagerFactoryBean devEntityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(devDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(DevDBConfig.class.getPackage().getName());

        return factoryBean;
    }

//    @Bean
    public PlatformTransactionManager devTransactionManager() {
        return new JpaTransactionManager(Objects.requireNonNull(devEntityManagerFactory().getObject()));
    }

//    @Bean
    DataSource devDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:54322/ge_apm")
                .driverClassName("org.postgresql.Driver")
                .username("postgres")
                .password("root")
                .build();
    }
}
