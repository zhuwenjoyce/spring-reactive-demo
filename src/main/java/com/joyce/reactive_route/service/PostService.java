package com.joyce.reactive_route.service;

import com.joyce.reactive_route.dao.PostRepository;
import com.joyce.reactive_route.exception.NotFoundException;
import com.joyce.reactive_route.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
@Component
@Slf4j
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse
                .ok()
                .body(postRepository.findAll(), PostModel.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(PostModel.class)
                .flatMap(post -> postRepository.save(post))
                .flatMap(post ->
                        ServerResponse.created(
                                URI.create("/posts/" + post.getId())
                        ).build()
                );
    }


    public Mono<ServerResponse> get(ServerRequest request) {
        return postRepository.findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(post ->
                        ServerResponse
                                .ok()
                                .body(Mono.just(post), PostModel.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> update(ServerRequest request) {
        return Mono.zip((data) -> {
                    // This two object from query DB and request body
                    PostModel originPost = (PostModel) data[0];
                    PostModel newPost = (PostModel) data[1];
                    originPost.setTitle(newPost.getTitle());
                    originPost.setContent(newPost.getContent());
                    return originPost;
                },
                postRepository.findById(Long.valueOf(request.pathVariable("id")))
                        .switchIfEmpty(Mono.error(new NotFoundException(Long.valueOf(request.pathVariable("id"))))),
                request.bodyToMono(PostModel.class)
        )
                .cast(PostModel.class)
                .flatMap(post -> postRepository.save(post))
                .flatMap(post -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
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
