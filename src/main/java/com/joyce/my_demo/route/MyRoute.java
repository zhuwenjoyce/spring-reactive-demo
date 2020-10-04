package com.joyce.my_demo.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/4
 */
@Configuration
public class MyRoute {

    /*
    RouterFunction 仅限于 ServerResponse 泛型
     */
    @Bean("monoRouterFunction")
    public RouterFunction<ServerResponse> route_for_mono(MyRouteController myRouteController) {
        return RouterFunctions
                .route(RequestPredicates.GET("/joyce/route/mono")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), myRouteController::helloMono);
    }

    @Autowired
    MyRouteController myRouteController;

    @Bean("multipleRouterFunction")
    public RouterFunction<?> routerFunction() {
        return route(
                        GET("/joyce/my-route/get-users")
                        .and(accept(MediaType.APPLICATION_JSON)), myRouteController::getUsers
                )
                .and(
                        route(GET("/api/user/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)), myRouteController::getUserById)
                );
    }
}
