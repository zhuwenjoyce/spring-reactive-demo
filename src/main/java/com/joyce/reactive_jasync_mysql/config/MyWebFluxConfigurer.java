package com.joyce.reactive_jasync_mysql.config;

import org.springframework.format.FormatterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.config.*;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/11
 */
public class MyWebFluxConfigurer implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void configurePathMatching(PathMatchConfigurer configurer) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").resourceChain(true).addResolver()
    }

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {

    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }


}
