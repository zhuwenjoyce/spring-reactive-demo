package com.joyce.github.spring_guides.gs_reactive_rest_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GsReactiveRestServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(GsReactiveRestServiceApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(GsReactiveRestServiceApplication.class, args);
		logger.info("spring-reactive-demo启动成功！");

		GreetingWebClient gwc = new GreetingWebClient();
		System.out.println(gwc.getResult());
	}
}
