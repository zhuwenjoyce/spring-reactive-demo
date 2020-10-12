package com.joyce.reactive_route.route;

import com.joyce.reactive_route.filter.MyFilter;
import com.joyce.reactive_route.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(PostService postService, MyFilter myFilter) {
        return route(GET("/posts"), postService::list)
                .andRoute(POST("/posts")
                        .and(contentType(MediaType.APPLICATION_JSON)), postService::save)
                .andRoute(GET("/posts/{id}"), postService::get)
                .andRoute(PUT("/posts/{id}")
                        .and(contentType(MediaType.APPLICATION_JSON)), postService::update)
                .andRoute(DELETE("/posts/{id}"), postService::delete)
                .filter(myFilter)
                ;
    }
}
