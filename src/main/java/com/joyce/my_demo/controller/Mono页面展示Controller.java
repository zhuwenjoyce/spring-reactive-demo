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
    public Map<String, String> normal() {
        Map map = new HashMap();
        map.put("time", "" + System.currentTimeMillis());
        return map;
    }

    @Value("${server.port}")
    Integer serverPort;

    @GetMapping("/joyce/mono/webclient_demo")
    public Mono<Map> httpget() {
        Mono<Map> mapMono = WebClient.create("http://localhost:" + serverPort)
                .get()
                .uri("/joyce/mono/normal")
                .retrieve()
                .bodyToMono(Map.class);

        // block方法会报错：java.lang.IllegalStateException: block()/blockFirst()/blockLast() are blocking, which is not supported in thread reactor-http-nio-2
//        mapMono.block();

        // subscribe方法并不会改变map对象，页面仍然显示：{"time":"1602224415513"}
        Disposable d = mapMono.subscribe(m -> {
                m.put("client-time", "" + System.currentTimeMillis());
                logger.info("time == " + m.get("time"));
        });

        return mapMono;
    }
}
