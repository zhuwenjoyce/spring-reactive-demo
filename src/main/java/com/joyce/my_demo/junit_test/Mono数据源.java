package com.joyce.my_demo.junit_test;

import com.joyce.my_demo.model.UserModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/4
 */
public class Mono数据源 {
    private static final Logger logger = LoggerFactory.getLogger(Mono数据源.class);

    @Test
    public void test_justOrEmpty(){
        List<UserModel> users = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));
        Mono.justOrEmpty(
                users.stream()
                        .filter(user -> {
                            return user.getId().equals(Long.valueOf(1));
                        })
                        .findFirst()
                        .orElse(null));
    }
}
