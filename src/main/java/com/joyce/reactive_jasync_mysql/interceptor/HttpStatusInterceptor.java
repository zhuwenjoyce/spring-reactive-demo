//package com.joyce.reactive_jasync_mysql.interceptor;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import java.io.PrintWriter;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @author: Joyce Zhu
// * @date: 2020/10/11
// */
//public class HttpStatusInterceptor  extends HandlerInterceptorAdapter {
//
//    private static final Logger logger = LoggerFactory.getLogger(HttpStatusInterceptor.class);
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json;charset=UTF-8");
//        if (Objects.isNull(ex)) {
//            logger.info("access uri[{}] successfully.", request.getRequestURI());
//            return;
//        }
//
//        logger.error("found exception when access uri[{}]. response status = {}, catch error = {}. request parameters = {}"
//                , request.getRequestURI(), response.getStatus(), ex.getMessage()
//                , request.getParameterMap().entrySet().stream().map(entry -> {
//                    String value = Objects.isNull(entry.getValue()) ? StringUtils.EMPTY : entry.getValue()[0];
//                    return entry.getKey() + " = " + value;
//                }).collect(Collectors.toList())
//                , ex);
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        try (PrintWriter writer = response.getWriter();) {
//            writer.print(EcouponResponse.error(ResponseConstant.CODE_9999, ResponseConstant.CODE_9999_MSG).toErrorJson());
//        }
//    }
//}
//
