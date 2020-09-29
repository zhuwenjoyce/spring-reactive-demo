package com.joyce.csdn.cn_hhaip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class UseScheduler {
    private static final Logger logger = LoggerFactory.getLogger(UseScheduler.class);
    public static void main(String[] args) {
        Flux.create(sink -> {
            String threadName = Thread.currentThread().getName();
            logger.info("thread name == " + threadName);
            sink.next(threadName);
            sink.complete();
        })
        .publishOn(Schedulers.single())
        .map(x -> String.format("《[%s] %s》", Thread.currentThread().getName(), x))
        .publishOn(Schedulers.elastic())
        .map(x -> String.format("。[%s] %s。", Thread.currentThread().getName(), x))
        .subscribeOn(Schedulers.parallel())
        .toStream()
        .forEach(s -> {
            logger.info("s == " + s);
        });
    }
}
