package com.joyce.csdn.zdshare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.joyce.csdn.zdshare"})
public class MyApplication {
	private static final Logger logger = LoggerFactory.getLogger(MyApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MyApplication.class, args);
		logger.info("com.joyce.csdn.zdshare.MyApplication 启动成功！");
	}
}
