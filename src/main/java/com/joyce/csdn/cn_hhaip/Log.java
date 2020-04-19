package com.joyce.csdn.cn_hhaip;

import reactor.core.publisher.Flux;

public class Log {
	public static void main(final String[] args) {
		Flux.range(1, 2).log("Range:::").subscribe(System.out::println);
	}
}
