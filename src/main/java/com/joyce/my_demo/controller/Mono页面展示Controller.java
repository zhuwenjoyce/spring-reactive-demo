package com.joyce.my_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/joyce/mono/webflux")
    public Mono<Map<String,String>> webflux(){
        Map map = new HashMap();
//        map.put("name","zhangsan");
        map.put("time", "" + System.currentTimeMillis());
        return Mono.just(map);
    }
}
