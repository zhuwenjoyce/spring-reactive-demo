package com.joyce.csdn.cn_hhaip;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import reactor.core.publisher.Flux;

public class FluxCreator {
    public static void main(final String[] args) {
//       simple();
       generate();
//       create();
    }

    private static void simple() {
		System.out.println("*********** 固定数据，逗号分隔分批次打印：");
		Flux.just("Hello", "World").subscribe(System.out::println);
		System.out.println("*********** 固定数据，集合遍历打印：");
		Flux.fromArray(new Integer[] {10, 20, 30}).subscribe(System.out::println);
		System.out.println("*********** 空值打印：");
		Flux.empty().subscribe(System.out::println);
		System.out.println("*********** 1-3区间数打印：");
		Flux.range(1, 3).subscribe(System.out::println);
		System.out.println("*********** 每隔10秒打印一次：");
		Flux.interval(Duration.of(10, ChronoUnit.MINUTES)).just("Hello", "World").subscribe(System.out::println);
		System.out.println("*********** 每隔2秒打印一次：");
		System.out.println("*********** 区间数，每隔2秒打印一次：");
		Flux.interval(Duration.ofSeconds(20)).range(100, 2).subscribe(System.out::println);
	}

	private static void generate() {
		System.out.println("*******");
		Flux.generate(sink -> {
			sink.next("Hello");
			sink.complete();
		}).subscribe(System.out::println);

		final Random random = new Random();
		Flux.generate(ArrayList::new, (list, sink) -> {
			int value = random.nextInt(100);
			list.add(value);
			sink.next(value);
			if (list.size() == 10) {
				sink.complete();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return list;
		}).subscribe(System.out::println);
	}

	private static void create() {
		System.out.println("*******");
		Flux<Integer> flux = Flux.create(sink -> {
			for (int i = 0; i < 10; i++) {
				sink.next(i);
			}
			sink.complete();
		});
		flux.subscribe(System.out::println);
	}
}
