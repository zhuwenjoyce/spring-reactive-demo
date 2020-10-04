package com.joyce.my_demo.route;

import com.joyce.my_demo.model.UserModel;
import com.joyce.my_demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/4
 */
@Component
public class MyRouteController {
    private static final Logger logger = LoggerFactory.getLogger(MyRouteController.class);

    public Mono<ServerResponse> helloMono(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Joyce! It's Mono"));
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/joyce/my-route/get-users")
    public Mono<ServerResponse> getUsers(ServerRequest request) {
        return ServerResponse.ok().body(userService.getUsers(), UserModel.class);
    }

    @RequestMapping("/joyce/my-route/get-user-by-id")
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        return userService.getUserById(request.pathVariable("id"))
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), UserModel.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
