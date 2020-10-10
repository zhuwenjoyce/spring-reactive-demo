package com.joyce.reactive_redis.dao;

import com.joyce.reactive_redis.model.PostModel;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
public interface PostRepository extends KeyValueRepository<PostModel, Long> {
}
