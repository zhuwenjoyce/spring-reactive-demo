package com.joyce.reactive_jasync_mysql.dao;

import com.joyce.reactive_jasync_mysql.model.PostModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/9
 */
public interface PostRepository extends ReactiveCrudRepository<PostModel, Long> {
}
