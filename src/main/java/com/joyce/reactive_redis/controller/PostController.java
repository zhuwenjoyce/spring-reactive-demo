package com.joyce.reactive_redis.controller;

import com.joyce.reactive_redis.dao.PostRepository;
import com.joyce.reactive_redis.model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("")
    public Flux<Iterable<PostModel>> list() {
        return Flux.just(postRepository.findAll());
    }

    @GetMapping("/init-data")
    public Map initData(){
        postRepository.deleteAll();
        Stream.of("Post One", "Post Two")
                .forEach(title -> postRepository.save(
                        PostModel.builder()
                                .id(new Random().nextLong())
                                .title(title)
                                .content("Content of " + title)
                                .build()
                ));
        HashMap map = new HashMap<>();
        map.put("status", "OK");
        return map;
    }
}
