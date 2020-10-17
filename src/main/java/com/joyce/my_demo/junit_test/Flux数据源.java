package com.joyce.my_demo.junit_test;

import com.joyce.my_demo.model.UserModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.util.Loggers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/27
 */
public class Flux数据源 {
    private static final Logger logger = LoggerFactory.getLogger(Flux数据源.class);
    private reactor.util.Logger reactiveLogger = Loggers.getLogger(Flux数据源.class);

    @Test
    public void test数据源_空值() {
        System.out.println("*********** 空值打印：");
        Flux.empty().subscribe(System.out::println);
    }

    @Test
    public void test单一数据源() {
        System.out.println("*********** 固定数据，逗号分隔分批次打印：");
        Flux.just("Hello", "World").subscribe(logger::info);
    }

    @Test
    public void test数据源_数组() {
        System.out.println("*********** 固定数据，集合遍历打印：");
        Flux.fromArray(new Integer[]{10, 20, 30}).subscribe(System.out::println);
        Flux.fromArray(new String[]{"a", "b", "c"}).subscribe(logger::info);
    }

    @Test
    public void test数据源_区间() {
        System.out.println("*********** 1-3区间数打印：");
        Flux.range(1, 3).subscribe(System.out::println);
        Flux.range(1, 3).subscribe(integer -> {
            logger.info(integer + "...");
        });
    }

    @Test
    public void test数据源_时间间隔获取数据() {
        System.out.println("*********** 每隔10秒打印一次：");
        Flux.interval(Duration.of(10, ChronoUnit.MINUTES)).just("Hello", "World").subscribe(System.out::println);
        System.out.println("*********** 区间数，每隔2秒打印一次：");
        Flux.interval(Duration.ofSeconds(20)).range(100, 2).subscribe(System.out::println);
    }

    @Test
    public void test数据源_generate_一次返回一个数据() {
        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
            sink.next("Hello2");
            sink.complete();
            sink.next("Hello3");
            sink.complete();
        }).subscribe(System.out::println);
    }

    @Test
    public void test数据源_generate_一次返回一组数据() {
        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();  // 下沉完成指令。如果没执行到这句，则会一直有输出打印
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return list;
        }).subscribe(System.out::println);
    }

    @Test
    public void test数据源_create() {
        Flux<String> flux = Flux.create(sink -> {
            for (int i = 0; i < 3; i++) {

                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                sink.next(i + "");
                sink.complete();
            }
            sink.complete();
            sink.next("998");
            sink.next("999");
            sink.complete();
        });
//        flux.log(reactiveLogger);
//        flux.subscribe();
        flux.subscribe(reactiveLogger::info);
//        flux.doOnNext(integer -> {
//            System.out.println("doOnNext ===== " + integer);
//        });
//        flux.blockFirst(Duration.ofSeconds(1));
    }

    @Test
    public void test数据源_fromIterable() throws InterruptedException {
        List<UserModel> users = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));
        Flux<UserModel> flux = Flux.fromIterable(users);
        flux.subscribe(obj -> {
            logger.info("subscribe == " + obj);
        });
        flux.doOnNext(integer -> {
            logger.info("doOnNext ===== " + integer);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(5000L);
    }

    @Test
    public void test数据源_from_mono() throws InterruptedException {

    }

}
