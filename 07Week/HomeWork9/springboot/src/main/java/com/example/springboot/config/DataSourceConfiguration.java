package com.example.springboot.config;

import com.example.springboot.enums.DataSourceEnum;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {

    @Value("${spring.datasource.url}")
    private String masterUrl;
    @Value("${spring.datasource.username}")
    private String masterUsername;
    @Value("${spring.datasource.password}")
    private String masterPassword;

    @Value("${slave.datasource.url}")
    private String slaveUrl;
    @Value("${slave.datasource.username}")
    private String slaveUsername;
    @Value("${slave.datasource.password}")
    private String slavePassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        CustomRoutingDataSource customRoutingDataSource = new CustomRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource masterDataSource = createMasterDataSource();
        targetDataSources.put(DataSourceEnum.MASTER, masterDataSource);
        targetDataSources.put(DataSourceEnum.SLAVE, createSlaveDataSource());
        customRoutingDataSource.setTargetDataSources(targetDataSources);

        customRoutingDataSource.setDefaultTargetDataSource(masterDataSource);

        return customRoutingDataSource;
    }

    public DataSource createMasterDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        System.out.println("masterUrl:" + masterUrl);
        System.out.println("masterUsername:" + masterUsername);
        System.out.println("masterUserPassword:" + masterPassword);
        hikariDataSource.setJdbcUrl(masterUrl);
        hikariDataSource.setUsername(masterUsername);
        hikariDataSource.setPassword(masterPassword);
        return hikariDataSource;
    }

    public DataSource createSlaveDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        System.out.println("slaveUrl:" + slaveUrl);
        System.out.println("slaveUsername:" + slaveUsername);
        System.out.println("slaveUserPassword:" + slavePassword);
        hikariDataSource.setJdbcUrl(slaveUrl);
        hikariDataSource.setUsername(slaveUsername);
        hikariDataSource.setPassword(slavePassword);
        return hikariDataSource;
    }
}
