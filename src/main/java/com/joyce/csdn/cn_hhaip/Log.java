package com.joyce.csdn.cn_hhaip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Log {
	static Logger logger = LoggerFactory.getLogger(Log.class);

	public static void main(final String[] args) {
		// demo
		Flux.range(1, 2).log("Range:::")
				.subscribe(System.out::println);

		// my code
		Flux<Integer> flux = Flux.range(1, 2).log("Range:::");
		flux.subscribe(System.out::println);
	}
}
