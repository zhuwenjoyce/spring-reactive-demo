package com.joyce.reactive_jasync_mysql.config;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.SSLConfiguration;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/9
 */
@Component
public class DatabaseConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.datasource.mysql.host}")
    private String host;

    @Value("${spring.datasource.mysql.username}")
    private String username;

    @Value("${spring.datasource.mysql.password}")
    private String password;

    @Value("${spring.datasource.mysql.port}")
    private Integer port;

    @Value("${spring.datasource.mysql.database}")
    private String database;

    @Override
    @Bean // 一定要把这个bean注入到spring，否则会报错database组装失败
    public ConnectionFactory connectionFactory() {
        SSLConfiguration sslConfiguration = new SSLConfiguration();
        com.github.jasync.sql.db.Configuration configuration = new com.github.jasync.sql.db.Configuration(
                username,
                host,
                port,
                password,
                database,
                sslConfiguration,
                Charset.forName("UTF-8")
        );

        MySQLConnectionFactory connectionFactory = new MySQLConnectionFactory(configuration);
        return new JasyncConnectionFactory(connectionFactory);
    }

}
