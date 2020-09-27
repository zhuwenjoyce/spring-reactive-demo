package com.joyce.my_demo;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/27
 */
public class Flux数据源 {

//    @Test
    public void test简单数据源(){
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

}
