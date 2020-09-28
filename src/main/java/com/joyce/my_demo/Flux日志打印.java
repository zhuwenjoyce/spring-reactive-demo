package com.joyce.my_demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/28
 */
public class Flux日志打印 {
    private static final Logger logger = LoggerFactory.getLogger(Flux日志打印.class);

    @Test
    public void test单一数据源(){
        System.out.println("*********** 固定数据，逗号分隔分批次打印：");
        Flux.just("Hello", "World").subscribe(logger::info);
    }

    @Test
    public void test数据源_数组(){
        System.out.println("*********** 固定数据，集合遍历打印：");
        Flux.fromArray(new Integer[] {10, 20, 30}).subscribe(System.out::println);
        Flux.fromArray(new String[] {"a", "b", "c"}).subscribe(logger::info);
    }

    @Test
    public void test数据源_区间(){
        System.out.println("*********** 1-3区间数打印：");
        Flux.range(1, 3).subscribe(System.out::println);
        Flux.range(1, 3).subscribe(integer -> {
            logger.info(integer + "...");
        });
    }
}
