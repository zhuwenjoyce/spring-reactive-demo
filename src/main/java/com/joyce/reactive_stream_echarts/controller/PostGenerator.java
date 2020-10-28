package com.joyce.reactive_stream_echarts.controller;

import com.joyce.reactive_stream_echarts.model.OnlineAmountModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/28
 */
@Slf4j
@Component
public class PostGenerator {
    private Random random = new Random();

    private final List<String> posts = Arrays.asList("Post One", "Post Two");

    private Instant instant = Instant.now();

    public Flux<OnlineAmountModel> fetchPostStream(Duration period) {

        return Flux.generate(() -> 0,
                (BiFunction<Integer, SynchronousSink<OnlineAmountModel>, Integer>) (index, sink) -> {
                    instant = instant.plus(1, ChronoUnit.SECONDS);
                    OnlineAmountModel onlineAmount = OnlineAmountModel.builder()
//                            .postOneAmount(random.nextInt(40) % (40) + 80)
//                            .postTwoAmount(random.nextInt(20) % (20) + 100)
                            .amount(random.nextInt(20) % (20) + 100)
                            .time(instant)
                            .build();

                    sink.next(onlineAmount);
                    return ++index % posts.size();
                })
                .zipWith(Flux.interval(period))
                .map(Tuple2::getT1)
                .share()
                .log();
    }
}
