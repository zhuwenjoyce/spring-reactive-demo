package com.joyce.reactive_jasync_mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MySQLApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySQLApplication.class, args);
    }
}

