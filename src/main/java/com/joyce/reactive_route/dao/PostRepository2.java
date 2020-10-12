package com.joyce.reactive_route.dao;

import com.alibaba.fastjson.JSON;
import com.joyce.reactive_route.model.PostModel;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/12
 */
@Component
public class PostRepository2 {

    private static final Logger logger = LoggerFactory.getLogger(PostRepository2.class);

    @Autowired
    ConnectionFactory connectionFactory;

    /*
    此方法不起作用！！！
    * */
    @Transactional
    public Mono<List<PostModel>> likeByTitle(String title) {
        Flux<Result> results = Mono.from(connectionFactory.create())
                .flatMapMany(connection -> {
                    return connection
                            .createStatement("SELECT id,title from t_post WHERE id = 5 ")
//                    .bind(0, title)
                            .execute()
                            ;
                });
        Mono<List<PostModel>> postModelFlux = results.flatMap(result -> result.map((row, rowMetadata) -> {
//               return row.get(0, PostModel.class);
            logger.info("row >>> " + JSON.toJSONString(row));
            PostModel model = new PostModel();
            model.setId(row.get(0, Long.class));
            model.setTitle(row.get(1, String.class));
            return model;
        })).collectList();
        List<PostModel> list = postModelFlux.block();
//        Result ss = results.blockFirst(Duration.ofSeconds(3));


        Mono<Result> monoResult = Mono.from(connectionFactory.create())
                .flatMap(c ->
                        Mono.from(
                                c.createStatement("SELECT id,title from t_post WHERE id = 5 ")
//                                c.createStatement("SELECT id,title from t_post WHERE title = $1")
//                                .bind("$1", title)
                                .execute()
                        )
                                .doFinally((st) -> {
                                    c.close();
                                })
                );
        monoResult.flatMap(result -> {
            Publisher<Object> m = result.map(((row, rowMetadata) -> {
                PostModel model = new PostModel();
                model.setId(row.get(0, Long.class));
                model.setTitle(row.get(1, String.class));
                return model;
            }));
            return null;
        });

        return postModelFlux;
    }
}
