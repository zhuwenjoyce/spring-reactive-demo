package com.joyce.csdn.zdshare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author: Joyce Zhu
 * @date: 2020/4/20
 */
@RestController
public class MyControllerClient {

    @GetMapping("/webclient_demo")
    public Mono<Map> httpget(){
        WebClient.ResponseSpec responseSpec = WebClient.create("http://localhost:8080")
                .get()
                .uri("/webflux")
                .retrieve();
        Mono<Map> mapMono = responseSpec.bodyToMono(Map.class);
        Disposable d = mapMono.subscribe(m -> m.put("client-time","" + System.currentTimeMillis()));
//         mapMono.take(Duration.ofSeconds(2)); // 每隔2秒获取一次map值, 没起作用
        return mapMono;
    }

}
