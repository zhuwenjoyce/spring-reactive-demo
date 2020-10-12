package com.joyce.reactive_jasync_mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class MySQLApplication {
    private static final Logger logger = LoggerFactory.getLogger(MySQLApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MySQLApplication.class, args);
    }
}

