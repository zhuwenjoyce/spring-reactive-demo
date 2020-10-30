package com.joyce.reactive_route.controller;

import com.joyce.reactive_route.dao.PostRepository_reactiveCrud;
import com.joyce.reactive_route.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.Loggers;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/29
 */
@Slf4j
@RestController
public class ReactiveCrudController {
    private static final reactor.util.Logger REACTIVE_LOGGER = Loggers.getLogger(ReactiveCrudController.class);
    @Autowired
    private PostRepository_reactiveCrud postReactiveRepository;

    // 尚未试验成功
    @PostMapping("/posts/getPostModelByCreateDate")
    public Flux<ServerSentEvent<PostModel>> getPostModelByCreateDate(LocalDateTime createDate) {
        log.info("exec PostService.getPostModelByCreateDate 执行开始。 param createDate = {}", createDate);

        Flux<PostModel> postModelFlux =
                postReactiveRepository.getPostModelByCreateDate2()
                .doOnError(ex -> log.error(ex.getMessage(), ex) )
                .log("getPostModelByCreateDate by dao::::");

        Flux<ServerSentEvent<PostModel>> result = postModelFlux
                .doOnError(ex -> log.error(ex.getMessage(), ex) )
                .log(REACTIVE_LOGGER)
                .map(postModel -> ServerSentEvent.<PostModel>builder()
                        .event("/posts/getPostModelByCreateDate")
                        .id( postModel.getId() + " --- " + postModel.getTitle())
                        .data(postModel)
                        .build());
        log.info("exec PostService.getPostModelByCreateDate 执行结束");
        return result;
    }

}
