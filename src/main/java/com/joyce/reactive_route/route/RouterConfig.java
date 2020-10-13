package com.joyce.reactive_route.route;

import com.joyce.reactive_route.filter.MyFilter1;
import com.joyce.reactive_route.filter.MyFilter2;
import com.joyce.reactive_route.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RouterConfig {

    private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);

    @Bean
    public RouterFunction<ServerResponse> routes(PostService postService, MyFilter1 myFilter1, MyFilter2 myFilter2) {

         // 第 1 种方式：最简单的route
         RouterFunction<ServerResponse> route1 = route(GET("/posts"), postService::list)
                .andRoute(POST("/posts")
                        .and(contentType(MediaType.APPLICATION_JSON)), postService::save)
                .andRoute(GET("/posts/{id}"), postService::get)
                .andRoute(PUT("/posts/{id}")
                        .and(contentType(MediaType.APPLICATION_JSON)), postService::update)
                .andRoute(DELETE("/posts/{id}"), postService::delete)
                .filter(myFilter1)
                ;


        // 第 2 种方式，带before和after和filter拦截器的
        RouterFunction<ServerResponse> route2 = route()
                .filter(myFilter1)
                .before(serverRequest -> {
                    logger.info(">>>>>>>> before all request !");
                    MultiValueMap<String, String> queryParams = serverRequest.queryParams();
                    queryParams.entrySet().stream().forEach( entry -> {
                                logger.info(">>>>>>>> before all request ! key = {}, value = {}", entry.getKey(), entry.getValue());
                    } );
                    return serverRequest;
                })
                .after((serverRequest, serverResponse) -> {
                    logger.info(">>>>>>>> after all response !");
                    return serverResponse;
                })
                .path("/posts", builder ->
                        builder.GET("", postService::list)
                        .GET("/{id}", postService::get)
                        .GET("/getPostModelById/{id}", postService::getPostModelById)
                        .POST("/greatThanID", postService::greatThanID)
                        .POST("/getPostModelByIdAndTitle", contentType(MediaType.APPLICATION_JSON), postService::getPostModelByIdAndTitle)
                        .filter(myFilter2)
                        .before(serverRequest -> {
                            logger.info(">>>>>>>> before request /posts ");
                            return serverRequest;
                        })
                        .after((serverRequest, serverResponse) -> {
                            logger.info(">>>>>>>> after response /posts !");
                            return serverResponse;
                        })
                )
//                .GET("/posts", postService::list)
//                .GET("/posts/{id}", postService::get)
                .POST("/posts", contentType(MediaType.APPLICATION_JSON), postService::save)
                .PUT("/posts/{id}", contentType(MediaType.APPLICATION_JSON), postService::update)
                .DELETE("/posts/{id}", postService::delete)
                .build()
                ;
        return route2;
    }
}
