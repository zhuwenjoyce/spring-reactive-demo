package com.joyce.csdn.zdshare.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MyController {

    @GetMapping("/")
    public Map<String,String> root(){
        Map map = new HashMap();
        map.put("msg","root");
        return map;
    }

    @GetMapping("/servletweb")
    public Map<String,String> servletweb(){
        Map map = new HashMap();
        map.put("msg","servletweb");
        return map;
    }
//
//    @GetMapping("webflux")
//    public Mono<Map<String,String>> webflux(){
//        return Mono.just(Map.of("msg","webflux"));
//    }

    @GetMapping("/webclient_demo")
    public Mono<Map> httpget(){
        return WebClient.create("http://localhost:8002")
                .get()
                .uri("/webflux")
                .retrieve()
                .bodyToMono(Map.class);
    }

    /**
     * @return 每间隔一秒就向页面返回一次内容：
     * id:0
     * event:random
     * data:1013692194
     *
     * id:1
     * event:random
     * data:-171434563
     *
     * id:2
     * event:random
     * data:1332124706
     *
     * id:3
     * event:random
     * data:1478961790
     */
    @GetMapping("/random_numbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt(10)))
                .map(data -> ServerSentEvent.<Integer>builder()
                            .event("random")
                            .id(Long.toString(data.getT1()))
                            .data(data.getT2())
                            .build());
    }

//    @GetMapping("/list-employee")
//    public Flux<ServerSentEvent<ArrayList>> randomNumbers() {
//        final Random random = new Random();
//        Flux<ServerSentEvent<ArrayList>> result = Flux.interval(Duration.ofSeconds(1))
//                .generate(ArrayList::new, (list, sink) -> {
//                    int value = random.nextInt(100);
//                    list.add(value);
//                    sink.next(value);
//                    if (list.size() == 10) {
//                        sink.complete();
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return list;
//                });
//        return result;
//    }

}
