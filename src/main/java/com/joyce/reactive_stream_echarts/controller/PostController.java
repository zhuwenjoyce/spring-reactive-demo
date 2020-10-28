package com.joyce.reactive_stream_echarts.controller;

import com.joyce.reactive_stream_echarts.model.OnlineAmountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/28
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostGenerator postGenerator;

    @GetMapping(value = "/online/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OnlineAmountModel> online() {
        return postGenerator
                .fetchPostStream(Duration.ofMillis(500))
                .share();
    }
}