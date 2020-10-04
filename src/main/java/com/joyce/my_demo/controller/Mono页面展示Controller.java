package com.joyce.my_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/30
 */
@RestController
public class Mono页面展示Controller {
    private static final Logger logger = LoggerFactory.getLogger(Mono页面展示Controller.class);

    @GetMapping("/joyce/mono/normal")
    public Map<String,String> normal(){
        Map map = new HashMap();
        map.put("msg","normal");
        return map;
    }

    @Value("${server.port}")
    Integer serverPort;

    @GetMapping("/joyce/mono/webflux")
    public Mono<Map<String,String>> webflux(){
        Map map = new HashMap();
//        map.put("name","zhangsan");
        map.put("time", "" + System.currentTimeMillis());
        return Mono.just(map);
    }

    @GetMapping("/joyce/mono/webclient_demo")
    public Mono<Map> httpget(){
        WebClient.ResponseSpec responseSpec = WebClient.create("http://localhost:" + serverPort)
                .get()
                .uri("/joyce/mono/webflux")
                .retrieve();
        Mono<Map> mapMono = responseSpec.bodyToMono(Map.class);
        Disposable d = mapMono.subscribe(m -> m.put("client-time","" + System.currentTimeMillis()));
//         mapMono.take(Duration.ofSeconds(2)); // 每隔2秒获取一次map值, 没起作用
        return mapMono;
    }
}
