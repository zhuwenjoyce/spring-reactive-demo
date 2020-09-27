package com.joyce.csdn.cn_hhaip;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class Hot {
    public static void main(String[] args) throws InterruptedException {
        final Flux<Long> source = Flux.interval(Duration.ofSeconds(1))
                .take(5)  // 拿5次
                .repeat(2) // 重复2遍，加上原始的那一遍，总共执行3遍
                .publish()
                .autoConnect();
//        source.subscribe();
        source.subscribe(l -> {
            System.out.println("l === " + l);
        });
        System.out.println("00000");
        source.toStream().forEach(System.out::println);
        Thread.sleep(500);
    }
}
