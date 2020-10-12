package com.joyce.reactive_route.dao;

import com.joyce.reactive_route.model.PostModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
public interface PostRepository extends ReactiveCrudRepository<PostModel, Long> {


    PostModel findByTitle(String title);
}








