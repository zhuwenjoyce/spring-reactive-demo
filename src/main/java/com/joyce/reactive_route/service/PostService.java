package com.joyce.reactive_route.service;

import com.alibaba.fastjson.JSON;
import com.joyce.reactive_route.dao.PostRepository;
import com.joyce.reactive_route.dao.PostRepository2;
import com.joyce.reactive_route.exception.NotFoundException;
import com.joyce.reactive_route.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
@Component
@Slf4j
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    PostRepository2 postRepository2;

    public Mono<ServerResponse> list(ServerRequest request) {
        logger.info("exec PostService.list");
        return ServerResponse
                .ok()
                .body(postRepository.findAll(), PostModel.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        logger.info("exec PostService.get");
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<PostModel> postModelMono = postRepository.findById(id);
        return postModelMono.flatMap(post ->
                        ServerResponse
                                .ok()
                                .body(Mono.just(post), PostModel.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> findByTitle(ServerRequest request) {
        logger.info("exec PostService.getByName.");
        MultiValueMap<String, String> queryParams = request.queryParams();
        Optional<String> titleOptional = request.queryParam("title");
        String title = titleOptional.get();
        logger.info("exec PostService.getByName. title = " + title);
        Mono<PostModel> postModel = postRepository.findByTitle(title);
//        postModel.is
        return ServerResponse.ok().body(postModel, PostModel.class);
    }

    public Mono<ServerResponse> getPostModelById(ServerRequest request) {
        logger.info("exec PostService.likeByTitle.");

        String idStr = request.pathVariable("id");
        Long id = Long.valueOf(idStr);
        logger.info("exec PostService.getPostModelById. id = " + id);

        // 如果返回为空，则给一个默认值
        PostModel defaultPostModel = new PostModel();
        defaultPostModel.setId(0L);
        defaultPostModel.setTitle("default-title");
        defaultPostModel.setContent("default-content");
        defaultPostModel.setCreateDate(LocalDateTime.now());

        Mono<PostModel> postModelMono = postRepository.getPostModelByFixationId();
        postModelMono = postModelMono.defaultIfEmpty(defaultPostModel); // 设置默认值
        postModelMono.subscribe(model -> { // subscribe可以打印出内容
           logger.info(" subscribe fixation id ========== " + JSON.toJSONString(model));
        });

        Mono<PostModel> postModelMono2 = postRepository.getPostModelById(id);
        postModelMono2 = postModelMono2.defaultIfEmpty(defaultPostModel); // 设置默认值
        postModelMono2.subscribe(postModel -> {
            logger.info(" subscribe 2 ========== " + JSON.toJSONString(postModel));
        });

        return ServerResponse.ok().body(postModelMono, PostModel.class);
    }

    public Mono<ServerResponse> greatThanID(ServerRequest request) {
        logger.info("exec PostService.greatThanID.");

        MultiValueMap<String, String> queryParams = request.queryParams();
        Optional<String> idOptional = request.queryParam("id");
        Long id = Long.valueOf(idOptional.get());
        logger.info("exec PostService.greatThanID. id = " + id);

        Mono<List<PostModel>> postModelList = postRepository2.greatThanID(id);

//      这行可以打印出内容，保留，并注释掉
//        postModelList.subscribe(postModel -> {
//           logger.info(" subscribe ========== " + JSON.toJSONString(postModel));
//        });

        return ServerResponse.ok().body(postModelList, PostModel.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        logger.info("exec PostService.save");
        return request.bodyToMono(PostModel.class)
                .flatMap(post -> postRepository.save(post))
                .flatMap(post ->
                        ServerResponse.created(
                                URI.create("/posts/" + post.getId())
                        ).build()
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        logger.info("exec PostService.update");
        String idStr = request.pathVariable("id");
        logger.info("update start. idStr = " + idStr);
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
                    logger.info("update post model. before save.");
                    Mono<PostModel> postModelMono = postRepository.save(post);
                    logger.info("update post model. after save.");
                    return postModelMono;
                })
                .flatMap(post -> ServerResponse.noContent().build());
        logger.info("update end.");
        return responseMono;
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        logger.info("exec PostService.delete");
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
