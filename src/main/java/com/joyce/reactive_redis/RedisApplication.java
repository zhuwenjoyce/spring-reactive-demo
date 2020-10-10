package com.joyce.reactive_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 要成功启动此类，需屏蔽mysql的依赖：spring-data-r2dbc、jasync-r2dbc-mysql 否则会启动失败，报database没有找到的错误
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}
