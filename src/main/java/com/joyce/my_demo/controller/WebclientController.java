package com.joyce.my_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.Loggers;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/7
 */
@RestController
public class WebclientController {
    private static final Logger logger = LoggerFactory.getLogger(WebclientController.class);

    private reactor.util.Logger reactiveLogger = Loggers.getLogger(WebclientController.class);

    @GetMapping("/joyce/webclient/normal")
    public Map<String,Object> normal() throws InterruptedException {
        Map map = new HashMap();
        map.put("time", ZonedDateTime.now());
        Thread.sleep(500L);
        return map;
    }

    private WebClient client = WebClient.create("http://localhost:8080");

    @GetMapping("/joyce/webclient/mono-1")
    public Mono<String> webclient_mono_在doOnNext方法里处理业务() {
        String str = client.get()
                .uri("/joyce/webclient/normal")
//                .accept(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_JSON)
//                .retrieve().bodyToMono(String.class).
//                .retrieve()
                .exchange()
                .flatMap(res -> res.bodyToMono(String.class))
                .log(reactiveLogger)
                .doOnError(e->{
                    logger.error(" doOnError -------------- " + e.getMessage(), e);
                })
                .doOnSuccess(s -> {
                    logger.info("打印顺序2，doOnSuccess -------------- s = " + s);
                })
                .doOnNext(s -> {
                    logger.info("打印顺序3，doOnNext -------------- s = " + s);
                })
                .subscribe()
                .toString()
                ;

        logger.info("打印顺序1，这行会先打印，所以先return返回了 ....");
        return Mono.justOrEmpty(str);
    }

    @GetMapping("/joyce/webclient/mono-2")
    public Mono<String> webclient_mono_block() {
        String str = client.get()
                .uri("/joyce/webclient/normal")
//                .accept(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(1))
                ;

        logger.info("打印顺序1，这行会先打印，所以先return返回了 ....");
        return Mono.justOrEmpty(str);
    }

    @GetMapping("/joyce/webclient/flux")
    public Mono<String> webclient_flux() {
        return null;
    }

}
