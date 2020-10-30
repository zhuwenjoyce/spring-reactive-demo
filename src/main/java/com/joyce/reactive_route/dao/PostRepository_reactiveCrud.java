package com.joyce.reactive_route.dao;

import com.joyce.reactive_route.model.PostModel;
import org.joda.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
public interface PostRepository_reactiveCrud extends ReactiveCrudRepository<PostModel, Long> {

    Mono<PostModel> findByTitle(String title);

    @Query("SELECT * FROM t_post where id = 15 ")
    Mono<PostModel> getPostModelByFixationId();

    // 这里一个参数，无论param里怎么写，: 冒号后面字符串乱写，都可以匹配上设置值，SQL都能正确执行
    @Query("SELECT * FROM t_post WHERE id = :id_abc ")
    Mono<PostModel> getPostModelById(@Param("id") Long id);

    @Query("SELECT * FROM t_post WHERE id = :id and title = :title ")
    Mono<PostModel> getPostModelByIdAndTitle(@Param("id") Long id, @Param("title") String title);

    @Query("SELECT * FROM t_post WHERE title = 'title11' ")
    Mono<PostModel> listPostModelLikeBy();

    @Query("SELECT * FROM t_post WHERE create_date >= '2020-10-10 23:22:35' ")
    Flux<PostModel> getPostModelByFixedCreateDate();

    @Query("SELECT * FROM t_post WHERE create_date >= :createDate ")
    Flux<PostModel> getPostModelByCreateDate(@Param("createDate") LocalDateTime createDate);
}








