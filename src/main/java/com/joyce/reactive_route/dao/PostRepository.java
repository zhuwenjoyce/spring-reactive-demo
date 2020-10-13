package com.joyce.reactive_route.dao;

import com.joyce.reactive_route.model.PostModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
public interface PostRepository extends ReactiveCrudRepository<PostModel, Long> {

    Mono<PostModel> findByTitle(String title);

    @Query("SELECT * FROM t_post where id = 15 ")
    Mono<PostModel> getPostModelByFixationId();

    @Query("SELECT * FROM t_post WHERE id = :age ")
    Mono<PostModel> getPostModelById(@Param("ideeee") Long id);

    @Query("SELECT * FROM t_post WHERE title = 'title11' ")
    Mono<PostModel> listPostModelLikeBy();
}








