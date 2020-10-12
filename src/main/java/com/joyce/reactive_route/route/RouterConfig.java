package com.joyce.reactive_route.route;

import com.joyce.reactive_route.filter.MyFilter;
import com.joyce.reactive_route.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RouterConfig {

    private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);

    @Bean
    public RouterFunction<ServerResponse> routes(PostService postService, MyFilter myFilter) {

         // 第 1 种方式：最简单的route
         RouterFunction<ServerResponse> route1 = route(GET("/posts"), postService::list)
                .andRoute(POST("/posts")
                        .and(contentType(MediaType.APPLICATION_JSON)), postService::save)
                .andRoute(GET("/posts/{id}"), postService::get)
                .andRoute(PUT("/posts/{id}")
                        .and(contentType(MediaType.APPLICATION_JSON)), postService::update)
                .andRoute(DELETE("/posts/{id}"), postService::delete)
                .filter(myFilter)
                ;

         // 第 2 种方式，带before和after和filter拦截器的
        RouterFunction<ServerResponse> route2 = route()
                .before(serverRequest -> {
                    logger.info(">>>>>>>> before !");
                    return serverRequest;
                })
                .after((serverRequest, serverResponse) -> {
                    logger.info(">>>>>>>> after !");
                    return serverResponse;
                })
                .GET("/posts", postService::list)
                .POST("/posts", contentType(MediaType.APPLICATION_JSON), postService::save)
                .GET("/posts/{id}", postService::get)
                .PUT("/posts/{id}", contentType(MediaType.APPLICATION_JSON), postService::update)
                .DELETE("/posts/{id}", postService::delete)
                .build()
                .filter(myFilter)
                ;


        // 第 3 种方式，带before和after和filter拦截器的
        RouterFunction<ServerResponse> route3 = route()
//                .path("/posts"
//                        , b1 -> b1.nest(
//                                        accept(MediaType.APPLICATION_JSON)
//                                        , b2 -> b2
//                                                .GET("/{id}", postService::get)
//                                                .GET("", postService::list)
//                                                .before(request ->
////                                                        {
////
////                                                            return request;
////                                                        }
//                                                    ServerRequest.from(request)
//                                                        .header("X-RequestHeader", "Value")
//                                                            .header("myname", "Joyce")
//                                                        .build())
//
//                                )
//                                .POST("", postService::save)
//                )
//                .after((request, response) -> {
//                    logger.info("execute after function in route function....... path = {}", request.path());
//                    return null;
//                })
                .before(serverRequest -> {
                    logger.info(">>>>>>>> before !");
                    return serverRequest;
                })
                .after((serverRequest, serverResponse) -> {
                    logger.info(">>>>>>>> after !");
                    return serverResponse;
                })
                .GET("/posts", postService::list)
                .POST("/posts", contentType(MediaType.APPLICATION_JSON), postService::save)
                .GET("/posts/{id}", postService::get)
                .PUT("/posts/{id}", contentType(MediaType.APPLICATION_JSON), postService::update)
                .DELETE("/posts/{id}", postService::delete)
                .build()
                .filter(myFilter)
                ;
        return route2;
    }
}
