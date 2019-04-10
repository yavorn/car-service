package com.telerikacademy.carservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("application.properties")
public class DataSourceConfig {

    private String dbUrl;
    private String username;
    private String password;

    @Autowired
    public DataSourceConfig(Environment environment) {
        dbUrl = environment.getProperty("database.url");
        username = environment.getProperty("database.username");
        password = environment.getProperty("database.password");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
