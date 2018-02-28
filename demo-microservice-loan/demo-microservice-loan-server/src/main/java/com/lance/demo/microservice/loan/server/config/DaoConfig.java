package com.lance.demo.microservice.loan.server.config;

//import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DaoConfig {
    @Bean
    @Primary
    public DataSource druidDataSource(DataSourceProperties dataSourceProperties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dataSourceProperties.getUrl());
        datasource.setUsername(dataSourceProperties.getUsername());
        datasource.setPassword(dataSourceProperties.getPassword());
        datasource.setDriverClassName(dataSourceProperties.getDriverClassName());
        datasource.setInitialSize(5);
        datasource.setMinIdle(5);
        datasource.setMaxActive(5);
        datasource.setMaxWait(5);
        datasource.setTimeBetweenEvictionRunsMillis(5000);
        datasource.setMinEvictableIdleTimeMillis(5000);
        datasource.setValidationQuery("select 1");
        //datasource.setTestWhileIdle(testWhileIdle);
        //datasource.setTestOnBorrow(testOnBorrow);
        //datasource.setTestOnReturn(testOnReturn);
        // datasource.setPoolPreparedStatements(dataSourceProperties.poo);
        return datasource;
    }
}
