package com.joyce.reactive_jasync_mysql.controller;

import com.joyce.reactive_jasync_mysql.dao.PostRepository;
import com.joyce.reactive_jasync_mysql.exception.NotFoundException;
import com.joyce.reactive_jasync_mysql.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/9
 */
@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public Flux<PostModel> list() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<PostModel> get(@PathVariable("id") Long id) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))));
    }

    @PutMapping("/{id}")
    public Mono<PostModel> update(@PathVariable("id") Long id, @RequestBody PostModel post) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                .map(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());
                    return p;
                })
                .flatMap(p -> postRepository.save(p));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PostModel> save(@RequestBody PostModel post) {
        // Couldn't get id in current time after save
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return postRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                .then(postRepository.deleteById(id));
    }
}
