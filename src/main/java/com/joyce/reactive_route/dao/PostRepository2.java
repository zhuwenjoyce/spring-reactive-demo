package com.joyce.reactive_route.dao;

import com.alibaba.fastjson.JSON;
import com.joyce.reactive_route.model.PostModel;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/12
 */
@Component
public class PostRepository2 {

    private static final Logger logger = LoggerFactory.getLogger(PostRepository2.class);

    @Autowired
    ConnectionFactory connectionFactory;

    /*
    此方法不起作用！！！
    * */
    @Transactional
    public Mono<List<PostModel>> greatThanID(Long id) {
//        MySqlConnectionConfiguration configuration2 = MySqlConnectionConfiguration.builder()
//                .host("127.0.0.1")
//                .user("root")
//                .port(3306) // optional, default 3306
//                .password("database-password-in-here") // optional, default null, null means has no password
//                .database("r2dbc") // optional, default null, null means not specifying the database
//                .serverZoneId(ZoneId.of("Continent/City")) // optional, default null, null means query server time zone when connection init
//                .connectTimeout(Duration.ofSeconds(3)) // optional, default null, null means no timeout
//                .sslMode(SslMode.VERIFY_IDENTITY) // optional, default SslMode.PREFERRED
//                .sslCa("/path/to/mysql/ca.pem") // required when sslMode is VERIFY_CA or VERIFY_IDENTITY, default null, null means has no server CA cert
//                .sslCert("/path/to/mysql/client-cert.pem") // optional, default has no client SSL certificate
//                .sslKey("/path/to/mysql/client-key.pem") // optional, default has no client SSL key
//                .sslKeyPassword("key-pem-password-in-here") // optional, default has no client SSL key password
//                .tlsVersion(TlsVersions.TLS1_3, TlsVersions.TLS1_2, TlsVersions.TLS1_1) // optional, default is auto-selected by the server
//                .sslHostnameVerifier(MyVerifier.INSTANCE) // optional, default is null, null means use standard verifier
//                .sslContextBuilderCustomizer(MyCustomizer.INSTANCE) // optional, default is no-op customizer
//                .zeroDateOption(ZeroDateOption.USE_NULL) // optional, default ZeroDateOption.USE_NULL
//                .useServerPrepareStatement() // Use server-preparing statements, default use client-preparing statements
//                .tcpKeepAlive(true) // optional, controls TCP Keep Alive, default is false
//                .tcpNoDelay(true) // optional, controls TCP No Delay, default is false
//                .autodetectExtensions(false) // optional, controls extension auto-detect, default is true
//                .extendWith(MyExtension.INSTANCE) // optional, manual extend an extension into extensions, default using auto-detect
//                .build();
//        ConnectionFactory connectionFactory = MySqlConnectionFactory.from(configuration2);

        Flux<Result> results = Mono.from(connectionFactory.create())
                .flatMapMany(connection -> {
                    return connection
                            .createStatement("SELECT id,title from t_post WHERE id = 5 ")
//                    .bind("$id", id)
                            .execute()
                            ;
                });
        Mono<List<PostModel>> postModelFlux = results.flatMap(result -> result.map((row, rowMetadata) -> {
//               return row.get(0, PostModel.class);
            logger.info("row >>> " + JSON.toJSONString(row));
            PostModel model = new PostModel();
            model.setId(row.get(0, Long.class));
            model.setTitle(row.get(1, String.class));
            return model;
        })).collectList();
        List<PostModel> list = postModelFlux.block();
//        Result ss = results.blockFirst(Duration.ofSeconds(3));


        Mono<Result> monoResult = Mono.from(connectionFactory.create())
                .flatMap(c ->
                        Mono.from(
                                c.createStatement("SELECT id,title from t_post WHERE id = 5 ")
//                                c.createStatement("SELECT id,title from t_post WHERE title = $1")
//                                .bind("$1", title)
                                .execute()
                        )
                                .doFinally((st) -> {
                                    c.close();
                                })
                );
        monoResult.flatMap(result -> {
            Publisher<Object> m = result.map(((row, rowMetadata) -> {
                PostModel model = new PostModel();
                model.setId(row.get(0, Long.class));
                model.setTitle(row.get(1, String.class));
                return model;
            }));
            return null;
        });

        return postModelFlux;
    }
}
