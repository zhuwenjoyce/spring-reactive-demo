package com.joyce.my_demo.junit_test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/28
 */
public class Flux数值操作 {
    private static final Logger logger = LoggerFactory.getLogger(Flux数值操作.class);

    @Test
    public void test_buffer_数值缓冲区() {
        Flux.range(1, 60).buffer(20).subscribe(System.out::println);
        /*
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
[21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40]
[41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60]
         */
    }

    @Test
    public void test_buffer_时间缓冲区() {
        Flux
                // 每1秒产生一个数据
                .interval(Duration.ofSeconds(1))
                // 5秒一个缓冲区，也就可以存储5个数据
                .buffer(Duration.ofMillis(5001))
                // 拿2次数据
                .take(2)
                .toStream()
                .forEach(i -> {
                    System.out.println(ZonedDateTime.now() + " === " + i);
                    /*
                    打印：
                    2020-09-28T13:57:48.238+08:00[Asia/Shanghai] === [0, 1, 2, 3, 4]
                    2020-09-28T13:57:53.224+08:00[Asia/Shanghai] === [5, 6, 7, 8, 9]
                    */
                });

    }

    @Test
    public void test_buffer_bufferUntil() {
        Flux.range(1, 10)
                // 满足一定条件作为一个缓冲区：直到可以整除2
                .bufferUntil(i -> i % 2 == 0)
                .subscribe(System.out::println);
        /*
        [1, 2]
        [3, 4]
        [5, 6]
        [7, 8]
        [9, 10]
        */
    }

    @Test
    public void test_buffer_bufferWhile() {
        Flux.range(1, 10)
                // 满足一定条件作为一个缓冲区，只有可以整除2
                .bufferWhile(i -> i % 2 == 0)
                .subscribe(System.out::println);
        /*
        [2]
        [4]
        [6]
        [8]
        [10]
        */
    }

    @Test
    public void test_buffer_filter() {
        Flux.range(1, 10)
                // 过滤出满足条件的：只有可以整除2
                .filter(i -> i % 2 == 0)
                .subscribe(System.out::println);
        /*
            2
            4
            6
            8
            10
        */
    }

    @Test
    public void test_window_按照时间窗口() {
        Flux.range(1, 10)
                .window(5)
                .subscribe(i -> {
                    System.out.println(ZonedDateTime.now() + " === " + i.getClass().getName());
                    i.subscribe(integer -> {
                        System.out.println(ZonedDateTime.now() + " ---- " + integer);
                    });
                });
        /*
            2020-09-28T14:12:23.388+08:00[Asia/Shanghai] === reactor.core.publisher.UnicastProcessor
            2020-09-28T14:12:23.389+08:00[Asia/Shanghai] ---- 1
            2020-09-28T14:12:23.389+08:00[Asia/Shanghai] ---- 2
            2020-09-28T14:12:23.389+08:00[Asia/Shanghai] ---- 3
            2020-09-28T14:12:23.389+08:00[Asia/Shanghai] ---- 4
            2020-09-28T14:12:23.389+08:00[Asia/Shanghai] ---- 5
            2020-09-28T14:12:23.390+08:00[Asia/Shanghai] === reactor.core.publisher.UnicastProcessor
            2020-09-28T14:12:23.390+08:00[Asia/Shanghai] ---- 6
            2020-09-28T14:12:23.390+08:00[Asia/Shanghai] ---- 7
            2020-09-28T14:12:23.390+08:00[Asia/Shanghai] ---- 8
            2020-09-28T14:12:23.390+08:00[Asia/Shanghai] ---- 9
            2020-09-28T14:12:23.390+08:00[Asia/Shanghai] ---- 10
        */
    }

    @Test
    public void test_window_按照时间窗口2() {
        Flux.interval(Duration.ofSeconds(1))
                .window(Duration.ofMillis(3001))
                .take(4) // 拿4次
                .toStream()
                .forEach(i -> {
                    System.out.println(ZonedDateTime.now() + " === " + i.getClass().getName());
                    i.subscribe(integer -> {
                        System.out.println(ZonedDateTime.now() + " ---- " + integer);
                    });
                });
        /*
            2020-09-28T15:32:42.251+08:00[Asia/Shanghai] === reactor.core.publisher.UnicastProcessor
            2020-09-28T15:32:43.245+08:00[Asia/Shanghai] ---- 0
            2020-09-28T15:32:44.242+08:00[Asia/Shanghai] ---- 1
            2020-09-28T15:32:45.242+08:00[Asia/Shanghai] ---- 2
            2020-09-28T15:32:45.243+08:00[Asia/Shanghai] === reactor.core.publisher.UnicastProcessor
            2020-09-28T15:32:46.243+08:00[Asia/Shanghai] ---- 3
            2020-09-28T15:32:47.242+08:00[Asia/Shanghai] ---- 4
            2020-09-28T15:32:48.241+08:00[Asia/Shanghai] ---- 5
            2020-09-28T15:32:48.244+08:00[Asia/Shanghai] === reactor.core.publisher.UnicastProcessor
            2020-09-28T15:32:49.245+08:00[Asia/Shanghai] ---- 6
            2020-09-28T15:32:50.244+08:00[Asia/Shanghai] ---- 7
            2020-09-28T15:32:51.244+08:00[Asia/Shanghai] ---- 8
            2020-09-28T15:32:51.244+08:00[Asia/Shanghai] === reactor.core.publisher.UnicastProcessor
        */
    }

    @Test
    public void test_zip() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .zipWith(Flux.just("e", "f"))
                .subscribe(System.out::println);
        /*
        [[a,c],e]
        [[b,d],f]
         */
    }

    @Test
    public void test_zip_指定方式压缩() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s--%s", s1, s2))
                .subscribe(System.out::println);
        /*
        [[a,c],e]
        [[b,d],f]
         */
    }

    @Test
    public void test_take() {
        Flux.range(1, 1000).take(3).subscribe(System.out::println);
        /*
        1
        2
        3
         */
    }

    @Test
    public void test_take_倒数() {
        Flux.range(1, 1000).takeLast(3).subscribe(System.out::println);
        /*
        998
        999
        1000
         */
    }

    @Test
    public void test_take_当值小于5() {
        Flux.range(1, 1000).takeWhile(i -> i < 5).subscribe(System.out::println);
        /*
        1
        2
        3
        4
         */
    }

    @Test
    public void test_take_当值直到5() {
        Flux.range(1, 1000).takeUntil(i -> i == 5).subscribe(System.out::println);
        /*
        1
        2
        3
        4
        5
         */
    }

    @Test
    public void test_reduce_自定义算法() {
        Flux.range(1, 5).reduce((x, y) -> {
            logger.info("x={},y={}", x, y);
           return x + y;
        }).subscribe(System.out::println);
        /*
        15:25:18.140 [main] DEBUG reactor.util.Loggers$LoggerFactory - Using Slf4j logging framework
        15:25:18.176 [main] INFO com.joyce.my_demo.Flux数据操作 - x=1,y=2
        15:25:18.177 [main] INFO com.joyce.my_demo.Flux数据操作 - x=3,y=3
        15:25:18.177 [main] INFO com.joyce.my_demo.Flux数据操作 - x=6,y=4
        15:25:18.177 [main] INFO com.joyce.my_demo.Flux数据操作 - x=10,y=5
        15
         */
    }

    @Test
    public void test_reduce_自定义算法_开头加一个运算数字100() {
        Flux.range(1, 5).reduceWith(() -> 100, (x, y) -> {
            logger.info("x={},y={}", x, y);
            return x + y;
        }).subscribe(System.out::println);
        /*
        16:54:43.978 [main] INFO com.joyce.my_demo.Flux数据操作 - x=100,y=1
        16:54:43.980 [main] INFO com.joyce.my_demo.Flux数据操作 - x=101,y=2
        16:54:43.980 [main] INFO com.joyce.my_demo.Flux数据操作 - x=103,y=3
        16:54:43.980 [main] INFO com.joyce.my_demo.Flux数据操作 - x=106,y=4
        16:54:43.980 [main] INFO com.joyce.my_demo.Flux数据操作 - x=110,y=5
        115
         */
    }

    @Test
    public void test_merge_两组take一起跑() {
        Flux.merge(Flux.interval(Duration.ofSeconds(2)).take(5), Flux.interval(Duration.ofSeconds(2)).take(5))
                .toStream()
                .forEach(i -> {
                    logger.info("" + i);
                });
        /*
        17:09:35.215 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:09:35.215 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:09:37.214 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:09:37.215 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:09:39.214 [main] INFO com.joyce.my_demo.Flux数值操作 - 2
        17:09:39.214 [main] INFO com.joyce.my_demo.Flux数值操作 - 2
        17:09:41.214 [main] INFO com.joyce.my_demo.Flux数值操作 - 3
        17:09:41.214 [main] INFO com.joyce.my_demo.Flux数值操作 - 3
        17:09:43.214 [main] INFO com.joyce.my_demo.Flux数值操作 - 4
        17:09:43.215 [main] INFO com.joyce.my_demo.Flux数值操作 - 4
         */
    }

    @Test
    public void test_merge_在第一组执行完毕之后merge后面所有的() {
        Flux.mergeSequential(Flux.interval(Duration.ofSeconds(2)).take(5), Flux.interval(Duration.ofSeconds(2)).take(5))
                .toStream()
                .forEach(i -> {
                    logger.info("" + i);
                });
        /*
        17:00:14.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:00:16.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:00:18.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 2
        17:00:20.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 3
        17:00:22.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 4
        17:00:22.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:00:22.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:00:22.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 2
        17:00:22.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 3
        17:00:22.613 [main] INFO com.joyce.my_demo.Flux数值操作 - 4
         */
    }

    @Test
    public void test_flatMap() {
        Flux.just(2, 3)
                .flatMap(x -> {
                    logger.info("x = " + x);
                    return Flux.interval(Duration.ofSeconds(2)).take(x);
                })
                .toStream()
                .forEach(i -> {
                    logger.info("" + i);
                });
        /*
        同时跑。第一组跑2次，第二组跑3次：
        17:14:05.812 [main] INFO com.joyce.my_demo.Flux数值操作 - x = 2
        17:14:05.826 [main] INFO com.joyce.my_demo.Flux数值操作 - x = 3
        17:14:07.829 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:14:07.829 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:14:09.826 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:14:09.827 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:14:11.828 [main] INFO com.joyce.my_demo.Flux数值操作 - 2
         */
    }

    @Test
    public void test_concatMap() {
        Flux.just(2, 3)
                .concatMap(x -> {
                    logger.info("x = " + x);
                    return Flux.interval(Duration.ofSeconds(2)).take(x);
                })
                .toStream()
                .forEach(i -> {
                    logger.info("" + i);
                });
        /*
        分开跑。第一组跑2次，跑完之后，第二组跑3次：
        17:17:31.593 [main] INFO com.joyce.my_demo.Flux数值操作 - x = 2
        17:17:33.609 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:17:35.611 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:17:35.611 [parallel-1] INFO com.joyce.my_demo.Flux数值操作 - x = 3
        17:17:37.616 [main] INFO com.joyce.my_demo.Flux数值操作 - 0
        17:17:39.612 [main] INFO com.joyce.my_demo.Flux数值操作 - 1
        17:17:41.616 [main] INFO com.joyce.my_demo.Flux数值操作 - 2
         */
    }

    @Test
    public void test_combineLatest() {
        Flux.combineLatest(
                Arrays::toString,
                Flux.interval(Duration.ofSeconds(2)).take(5),
                Flux.interval(Duration.ofSeconds(2)).take(5),
                Flux.interval(Duration.ofSeconds(2)).take(5)
        ).toStream()
                .forEach(i -> {
                    logger.info("" + i.getClass().getName() + ", " + i);
                });
        /*
        一个一个连接进来：
        17:28:42.829 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [0, 0, 0]
        17:28:44.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [1, 0, 0]
        17:28:44.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [1, 1, 0]
        17:28:44.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [1, 1, 1]
        17:28:46.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [2, 1, 1]
        17:28:46.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [2, 2, 1]
        17:28:46.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [2, 2, 2]
        17:28:48.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [3, 2, 2]
        17:28:48.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [3, 3, 2]
        17:28:48.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [3, 3, 3]
        17:28:50.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [4, 3, 3]
        17:28:50.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [4, 4, 3]
        17:28:50.828 [main] INFO com.joyce.my_demo.Flux数值操作 - java.lang.String, [4, 4, 4]
         */
    }
}
