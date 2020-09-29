package com.joyce.my_demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/28
 */
public class Flux自定义log {
    private static final Logger logger = LoggerFactory.getLogger(Flux自定义log.class);

    @Test
    public void test_log1(){
        Flux.range(1, 3).log("Range:::")
                .subscribe(System.out::println);
    }
    @Test
    public void test_log2(){
        Flux.just("a", "b").log(":::")
                .subscribe(System.out::println);
    }
}
