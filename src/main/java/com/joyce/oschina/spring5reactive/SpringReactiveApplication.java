package com.joyce.oschina.spring5reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringReactiveApplication{
	private static final Logger logger = LoggerFactory.getLogger(SpringReactiveApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringReactiveApplication.class, args);
		logger.info("spring-reactive-demo启动成功！");
	}
}
