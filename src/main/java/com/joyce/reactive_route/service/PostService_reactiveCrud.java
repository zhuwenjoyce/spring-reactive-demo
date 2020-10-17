package com.joyce.reactive_route.service;

import com.alibaba.fastjson.JSON;
import com.joyce.reactive_route.dao.PostRepository_reactiveCrud;
import com.joyce.reactive_route.exception.NotFoundException;
import com.joyce.reactive_route.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.Loggers;

import java.net.URI;
import java.util.Optional;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
@Component
@Slf4j
public class PostService_reactiveCrud {
    private reactor.util.Logger reactiveLogger = Loggers.getLogger(PostService_reactiveCrud.class);

    // 如果返回为空，则给一个默认值
    static PostModel defaultPostModel = new PostModel();
    static {
        defaultPostModel.setId(0L);
        defaultPostModel.setTitle("default-title");
        defaultPostModel.setContent("default-content");
        defaultPostModel.setCreateDate(LocalDateTime.now());
    }

    @Autowired
    private PostRepository_reactiveCrud postRepository;

    public Mono<ServerResponse> list(ServerRequest request) {
        log.info("exec PostService.list");
        return ServerResponse
                .ok()
                .body(postRepository.findAll(), PostModel.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        log.info("exec PostService.get");
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<PostModel> postModelMono = postRepository.findById(id);
        return postModelMono.flatMap(post ->
                        ServerResponse
                                .ok()
                                .body(Mono.just(post), PostModel.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> findByTitle(ServerRequest request) {
        log.info("exec PostService.getByName.");
        MultiValueMap<String, String> queryParams = request.queryParams();
        Optional<String> titleOptional = request.queryParam("title");
        String title = titleOptional.get();
        log.info("exec PostService.getByName. title = " + title);
        Mono<PostModel> postModel = postRepository.findByTitle(title);
//        postModel.is
        return ServerResponse.ok().body(postModel, PostModel.class);
    }

    public Mono<ServerResponse> getPostModelById(ServerRequest request) {
        log.info("exec PostService.getPostModelById.");

        String idStr = request.pathVariable("id");
        Long id = Long.valueOf(idStr);
        log.info("exec PostService.getPostModelById. id = " + id);

        Mono<PostModel> postModelMono = postRepository.getPostModelByFixationId();
        postModelMono = postModelMono.defaultIfEmpty(defaultPostModel); // 设置默认值
        postModelMono.subscribe(model -> { // subscribe可以打印出内容
           log.info(" subscribe fixation id ========== " + JSON.toJSONString(model));
        });

        Mono<PostModel> postModelMono2 = postRepository.getPostModelById(id);
        postModelMono2 = postModelMono2.defaultIfEmpty(defaultPostModel); // 设置默认值
        postModelMono2.subscribe(postModel -> {
            log.info(" subscribe 2 ========== " + JSON.toJSONString(postModel));
        });

        return ServerResponse.ok().body(postModelMono, PostModel.class);
    }

    // 尚未试验成功
    public Mono<ServerResponse> getPostModelByIdAndTitle(ServerRequest request) {
        log.info("exec PostService.getPostModelByIdAndTitle. 执行开始");

        // 这行代码能拿到post请求中json传参参数
//        Mono<PostModel> requestParamPostModel = request.bodyToMono(PostModel.class);
//        Mono<PostModel> postModelMono = requestParamPostModel.flatMap(postModel -> postRepository.getPostModelByIdAndTitle(postModel.getId(), postModel.getTitle()));
//        return ServerResponse.ok().body(postModelMono, PostModel.class);

        // 像这样直接返回代码更简洁一点
        Mono<ServerResponse> serverResponseMono = request.bodyToMono(PostModel.class)
                .flatMap(model -> {
                        Mono<PostModel> mono = postRepository.getPostModelByIdAndTitle(model.getId(), model.getTitle());
                        mono = mono.defaultIfEmpty(defaultPostModel);
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
        log.info("exec PostService.getPostModelByIdAndTitle 执行结束");
        return serverResponseMono;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        log.info("exec PostService.save");
        return request.bodyToMono(PostModel.class)
                .flatMap(post -> postRepository.save(post))
                .flatMap(post ->
                        ServerResponse.created(
                                URI.create("/posts/" + post.getId())
                        ).build()
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("exec PostService.update");
        String idStr = request.pathVariable("id");
        log.info("update start. idStr = " + idStr);
        Mono<ServerResponse> responseMono = Mono.zip((data) -> {
                    // This two object from query DB and request body
                    PostModel originPost = (PostModel) data[0];
                    PostModel newPost = (PostModel) data[1];
                    originPost.setTitle(newPost.getTitle());
                    originPost.setContent(newPost.getContent());
                    return originPost;
                },
                postRepository.findById(Long.valueOf(idStr))
                        .switchIfEmpty(Mono.error(new NotFoundException(Long.valueOf(request.pathVariable("id"))))),
                request.bodyToMono(PostModel.class)
        )
                .cast(PostModel.class)
                .flatMap(post -> {
                    log.info("update post model. before save.");
                    Mono<PostModel> postModelMono = postRepository.save(post);
                    log.info("update post model. after save.");
                    return postModelMono;
                })
                .flatMap(post -> ServerResponse.noContent().build());
        log.info("update end.");
        return responseMono;
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.info("exec PostService.delete");
        String idStr = request.pathVariable("id");
        Long id = Long.valueOf(idStr);
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(id)))
                .then(postRepository.deleteById(id))
                .then(ServerResponse
                        .noContent()
                        .build(postRepository.deleteById(id)));
    }


}
