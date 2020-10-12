package com.joyce.reactive_jasync_mysql.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/11
 */
@Component
public class MyWebExceptionHandler implements WebExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyWebExceptionHandler.class);

    @PostConstruct
    public void init(){
        logger.info("init MyWebExceptionHandler");
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        logger.error("出错了：" + ex.getMessage(), ex);
        return null;
    }
}
