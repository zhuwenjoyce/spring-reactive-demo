package com.joyce.my_demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/28
 */
public class FluxError处理 {
    private static final Logger logger = LoggerFactory.getLogger(FluxError处理.class);

    @Test
    public void test_打印错误() {
        Flux.just("a", "b")
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(logger::info, e->{logger.error(e.getMessage(), e);}); // 因为发生了error，所以打印了error日志
    }
    @Test
    public void test_当发生错误则返回指定值() {
        Flux.just("a", "b")
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn("myErr")
                .subscribe(logger::info);
    }
    @Test
    public void test_当发生错误_doOnError() {
        Flux.just("a", "b")
                .concatWith(Mono.error(new IllegalStateException()))
                .doOnError( e -> {logger.error("发生错误了：：：" + e.getMessage(), e);})
                .subscribe(logger::info);
    }
    @Test
    public void test_当发生错误_自定义处理错误() {
        Flux.just("a", "b")
                .concatWith(Mono.error(new IllegalArgumentException()))
                .onErrorResume(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just("error1");
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just("error2");
                    }
                    return Mono.empty();
                })
                .subscribe(logger::info);
    }
    @Test
    public void test_当发生错误_retry重试() {
        Flux.just("a", "b")
                .concatWith(Mono.error(new IllegalArgumentException()))
                .retry(1)
                .onErrorResume(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just("error1");
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just("error2");
                    }
                    return Mono.empty();
                })
                .subscribe(logger::info);
    }
}
