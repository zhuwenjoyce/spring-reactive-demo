package com.joyce.csdn.cn_hhaip;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class Hot {
    public static void main(String[] args) throws InterruptedException {
        final Flux<Long> source = Flux.interval(Duration.ofSeconds(2))
                .take(10)
                .publish()
                .autoConnect();
        source.subscribe();
        source.toStream().forEach(System.out::println);
        Thread.sleep(1000);
    }
}
