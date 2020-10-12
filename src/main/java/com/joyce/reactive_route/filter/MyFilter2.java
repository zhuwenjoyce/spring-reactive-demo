package com.joyce.reactive_route.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/11
 */
@Component
public class MyFilter2 implements HandlerFilterFunction<ServerResponse, ServerResponse> {
    private static final Logger logger = LoggerFactory.getLogger(MyFilter2.class);

        @Override
        public Mono<ServerResponse> filter(ServerRequest serverRequest,
                                           HandlerFunction<ServerResponse> handlerFunction) {
            logger.info(">>>>>>>>> myFilter 2 start.");
//            if (serverRequest.pathVariable("name").equalsIgnoreCase("test")) {
//                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
//            }
            return handlerFunction.handle(serverRequest);
        }
}
