package com.joyce.reactive_route.dao;

import com.alibaba.fastjson.JSON;
import com.joyce.reactive_route.model.PostModel;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/12
 */
@Component
@Slf4j
public class PostRepository_r2dbc {

    @Autowired
    ConnectionFactory connectionFactory;

    public Mono<List<PostModel>> greatThanID(Long id) {
        Mono<List<PostModel>> postModelFlux = Mono.from(connectionFactory.create())
                .flatMapMany(connection -> {
                    return connection
                            .createStatement("SELECT id,title from t_post WHERE id > ? ")
                            .bind(0, id) // 这里不支持bind name-value 的字符串格式占位符
                            .execute()
                            ;
                })
                .flatMap(result -> result.map((row, rowMetadata) -> {
                    log.info("row >>> " + JSON.toJSONString(row));
                    PostModel model = new PostModel();
                    model.setId(row.get(0, Long.class));
                    model.setTitle(row.get(1, String.class));
                    return model;
                }))
                .collectList();
        return postModelFlux;
    }

    public Flux<PostModel> insertPost(String title) {
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        Mono<Integer> count = databaseClient.execute(
                "INSERT INTO t_post (title, content, createDate) VALUES($1, $2, $3)")
                .bind("$1", title)
                .bindNull("$2", String.class)
                .bind("$3", LocalDate.now())
                .fetch()
                .rowsUpdated();
//        Flux<Map<String, Object>> rows = databaseClient.execute("SELECT title, content, createDate FROM t_post")
//                .fetch()
//                .all()
//                ;
        log.info("insert count == " + count);
        return databaseClient.execute("SELECT id, title, content, create_date FROM t_post")
                .map((row, rowMetadata) -> {
                    log.info("row >>> " + JSON.toJSONString(row));
                    PostModel model = new PostModel();
                    model.setId(row.get(0, Long.class));
                    model.setTitle(row.get("title", String.class));
                    model.setContent(row.get("content", String.class));
                    java.time.LocalDateTime t = row.get(3, java.time.LocalDateTime.class);
                    LocalDateTime d = new LocalDateTime(t.getYear(), t.getMonthValue(), t.getDayOfMonth(), t.getHour(), t.getMinute(), t.getSecond());
                    model.setCreateDate(d);
                    return model;
                })
        .all()
        ;
    }
}
