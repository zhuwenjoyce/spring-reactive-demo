package com.joyce.my_demo.junit_test;

import com.joyce.my_demo.model.UserModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.Loggers;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/4
 */
public class Mono数据源 {
    private static final Logger logger = LoggerFactory.getLogger(Mono数据源.class);
    private reactor.util.Logger reactiveLogger = Loggers.getLogger(Mono数据源.class);

    @Test
    public void test_justOrEmpty() {
        List<UserModel> users = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));
        Mono.justOrEmpty(
                users.stream()
                        .filter(user -> {
                            return user.getId().equals(Long.valueOf(1));
                        })
                        .findFirst()
                        .orElse(null));
    }

    @Test
    public void test_justOrEmpty_for_list() {
        List<UserModel> list = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));
        List<UserModel> users = Mono.justOrEmpty(list).log(reactiveLogger).block();
        users.forEach(u -> {
            logger.info("u.username = " + u.getUsername());
        });
    }

    @Test
    public void test_create() {
        List<UserModel> list = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));
        List<UserModel> users = (List<UserModel>) Mono.create(sink -> {
            sink.success(list);
        })
                .log(reactiveLogger)
                .block();
        users.forEach(u -> {
            logger.info("u.username = " + u.getUsername());
        });
    }

    @Test
    public void test_fromRunnable() {
        List<UserModel> list = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));
        Object runnable = Mono.fromRunnable(new Runnable() {
            @Override
            public void run() {
                logger.info("I'm a runnable.");
            }
        })
                .block();
    }

    @Test
    public void test_from() {
        List<UserModel> list = Arrays.asList(new UserModel(1L, "User1"), new UserModel(2L, "User2"));
        List<UserModel> users = Mono.from(Mono.justOrEmpty(list))
                .block();
        logger.info("users = " + users);
        String str = Mono.from(Mono.justOrEmpty("abc"))
                .block();
        logger.info("str = " + str);
    }

    @Test
    public void test_block() {
        String str = Mono.justOrEmpty("abc").block();
        logger.info("str = " + str);
        Disposable disposable = Mono.justOrEmpty("abc")
                .subscribe(s -> {
//                    logger.info("s = " + s);
                })
                ;
        logger.info("disposable.isDisposed() = " + disposable.isDisposed());
    }
}
