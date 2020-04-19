package com.joyce.baeldung.spring_webflux;

import org.springframework.context.annotation.Bean;

/**
 * 为了限制对该方法的访问，让我们创建SecurityConfig并定义一些基于路径的规则以仅允许ADMIN用户
 */
//@EnableWebFluxSecurity
//public class EmployeeWebSecurityConfig {
//
//    // ...
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(
//            ServerHttpSecurity http) {
//        http.csrf().disable()
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.POST, "/employees/update").hasRole("ADMIN")
//                .pathMatchers("/**").permitAll()
//                .and()
//                .httpBasic();
//        return http.build();
//    }
//}
