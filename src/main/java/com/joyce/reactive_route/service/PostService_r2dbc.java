package com.joyce.reactive_route.service;

import com.alibaba.fastjson.JSON;
import com.joyce.reactive_route.dao.PostRepository_r2dbc;
import com.joyce.reactive_route.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Loggers;

import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/13
 */
@Component
@Slf4j
public class PostService_r2dbc {
    private reactor.util.Logger reactiveLogger = Loggers.getLogger(PostService_r2dbc.class);

    @Autowired
    private PostRepository_r2dbc postRepository;

    // 如果返回为空，则给一个默认值
    static PostModel defaultPostModel = new PostModel();
    static {
        defaultPostModel.setId(0L);
        defaultPostModel.setTitle("default-title");
        defaultPostModel.setContent("default-content");
        defaultPostModel.setCreateDate(LocalDateTime.now());
    }

    // 尚未试验成功
    public Mono<ServerResponse> greatThanID(ServerRequest request) {
        log.info("exec PostService_r2dbc.greatThanID.");

        // 用post方法的json传参，用这种方式可以接收到参数
        Mono<PostModel> postModelMono = request.bodyToMono(PostModel.class);
        return postModelMono
                .flatMap(model -> {
                    Mono<List<PostModel>> mono = postRepository.greatThanID(model.getId());
//                    mono = mono.defaultIfEmpty(defaultPostModel);
                    mono.subscribe(m->{
                        log.info("m ==== " + JSON.toJSONString(m));
                    });
                    return mono;
                })
                .flatMap(postModel -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.justOrEmpty(postModel), PostModel.class);
                })
        ;

    }

    public Mono<ServerResponse> insertPost(ServerRequest serverRequest) {
        log.info("exec PostService_r2dbc.greatThanID.");

        // 用post方法的json传参，用这种方式可以接收到参数
        Mono<PostModel> postModelMono = serverRequest.bodyToMono(PostModel.class);
        Mono<List<PostModel>> monoList = postModelMono
                .flatMapMany(model -> {
                    Flux<PostModel> flux = postRepository.insertPost(model.getTitle());
//                    flux = flux.defaultIfEmpty(defaultPostModel);
                    flux.subscribe(m->{
                        log.info("postModel.class.name ==== " + m.getClass().getName() + ", m ==== " + JSON.toJSONString(m));
                    });
                    return flux;
                })
                .collectList()
                ;
        
        return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(monoList, PostModel.class);
    }
}
