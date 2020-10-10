package com.joyce.reactive_redis.config;

import com.joyce.reactive_redis.dao.PostRepository;
import com.joyce.reactive_redis.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Stream;

/**
 * CommandLineRunner的实现类会在系统启动时自动加载运行
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        postRepository.deleteAll();
        Stream.of("Post One", "Post Two")
                .forEach(title -> postRepository.save(
                        PostModel.builder()
                                .id(new Random().nextLong())
                                .title(title)
                                .content("Content of " + title)
                                .build()
                ));
    }
}