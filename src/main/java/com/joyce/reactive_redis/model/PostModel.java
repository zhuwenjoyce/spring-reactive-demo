package com.joyce.reactive_redis.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/9
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("posts") // 这个注解非常重要，它可以将此类作为repository类的泛型key存在，以便存入redis缓存
public class PostModel {

    @Id
    private Long id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createDate;
}
