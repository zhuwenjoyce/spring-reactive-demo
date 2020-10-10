package com.joyce.my_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class MyDemoReactiveApplication {
	private static final Logger logger = LoggerFactory.getLogger(MyDemoReactiveApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MyDemoReactiveApplication.class, args);
		logger.info("spring-reactive-demo启动成功！");
	}
}
