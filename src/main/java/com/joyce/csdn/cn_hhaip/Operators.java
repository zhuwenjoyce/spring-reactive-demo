package com.joyce.csdn.cn_hhaip;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
@Slf4j
public class Operators {
    public static void main(String[] args) {
//        printLine("buffer");
//        buffer();
//        printLine("filter");
//        filter();
//        printLine("window");
//        window();
        printLine("zipWith");
        zipWith();
//        printLine("take");
//        take();
//        printLine("reduce");
//        reduce();
//        printLine("merge");
//        merge();
//        printLine("flatMap");
//        flatMap();
//        printLine("concatMap");
//        concatMap();
//        printLine("combineLatest");
//        combineLatest();
    }

    private static void buffer() {
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.interval(Duration.ofSeconds(1)).buffer(Duration.ofMillis(1001)).take(2).toStream().forEach(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
    }

    private static void filter() {
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
    }

    private static void window() {
        Flux.range(1, 100).window(20).subscribe(System.out::println);
        Flux.interval(Duration.ofSeconds(2)).window(Duration.ofMillis(1001)).take(2).toStream().forEach(System.out::println);
    }

    private static void zipWith() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
    }

    private static void take() {
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
    }

    @Test
    public void reduce() {
        Flux.range(1, 3).reduce((x, y) -> {
            log.info("x = " + x + ", y = " + y);
            return x + y;
        }).subscribe(System.out::println);
        Flux.range(1, 3).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);
        /*
        3
        103
        */
    }

    private static void merge() {
        Flux.merge(Flux.interval(Duration.ofSeconds(2)).take(5), Flux.interval(Duration.ofSeconds(2)).take(5))
                .toStream()
                .forEach(System.out::println);
        Flux.mergeSequential(Flux.interval(Duration.ofSeconds(2)).take(5), Flux.interval(Duration.ofSeconds(2)).take(5))
                .toStream()
                .forEach(System.out::println);
    }

    private static void flatMap() {
        Flux.just(5, 10)
                .flatMap(x -> Flux.interval(Duration.ofSeconds(2)).take(x))
                .toStream()
                .forEach(System.out::println);
    }

    private static void concatMap() {
        Flux.just(5, 10)
                .concatMap(x -> Flux.interval(Duration.ofSeconds(2)).take(x))
                .toStream()
                .forEach(System.out::println);
    }

    private static void combineLatest() {
        Flux.combineLatest(
                Arrays::toString,
                Flux.interval(Duration.ofSeconds(2)).take(5),
                Flux.interval(Duration.ofSeconds(2)).take(5)
        ).toStream()
                .forEach(System.out::println);
    }

    private static void printLine(final String operator) {
        System.out.printf(
                "%s %s %s%n",
                StringUtils.repeat("*", 10),
                operator,
                StringUtils.repeat("*", 10)
        );
    }
}
