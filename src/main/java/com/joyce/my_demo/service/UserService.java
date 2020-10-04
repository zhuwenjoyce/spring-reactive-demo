package com.joyce.my_demo.service;

import com.joyce.my_demo.model.UserModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/4
 */
@Component
public class UserService {

    private final List<UserModel> users = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));

    public Mono<UserModel> getUserById(String id) {
        return Mono.justOrEmpty(
                users.stream()
                        .filter(user -> {
                            return user.getId().equals(Long.valueOf(id));
                        })
                        .findFirst()
                        .orElse(null));
    }

    public Flux<UserModel> getUsers() {
        return Flux.fromIterable(users);
    }
}
