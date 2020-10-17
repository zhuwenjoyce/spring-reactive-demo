package com.joyce.reactive_route.service;

import com.alibaba.fastjson.JSON;
import com.joyce.reactive_route.RouteApplicationTests;
import com.joyce.reactive_route.model.PostModel;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import java.net.URI;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

;
;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/10
 */
public class PostServiceReactiveCrudTest extends RouteApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceReactiveCrudTest.class);

    @Autowired
    ApplicationContext context;

    WebTestClient client;

    /**
     * Set configuration
     *
     * @throws Exception throw exception when failed
     */
    @Before
    public void setUp() throws Exception {
        client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .baseUrl("http://locahost:8080/")
                .build();
    }

    @After
    public void tearDown() throws Exception {
        logger.info("tear down !!!");
    }

    /**
     * Test list interface
     */
    @Test
    public void list() {
        // 第一种方法
        client.get()
                .uri("/posts/")
                .exchange()
                .expectStatus()
                .isOk()
                ;

        // 第二种方法，可以得到返回对象
        FluxExchangeResult<PostModel> result = client.get()
                .uri("/posts/")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(PostModel.class);
                ;
        Flux<PostModel> postModelFlux = result.getResponseBody();
        // 一条一条打印出list数据
        postModelFlux.subscribe(postModel -> {
            logger.info("postModel ====== " + JSON.toJSONString(postModel));
        });

    }

    /**
     * Test save interface
     */
    @Test
    public void save() {
        String title = "Test Post interface";
        String content = "Content of Test Post interface";
        URI location = saveAndValidatePost(title, content);
        getAndValidatePost(title, content, location);
    }

    /**
     * Test get interface
     */
    @Test
    public void get() {
        String title = "Test Get interface";
        String content = "Content of Test Get interface";
        URI location = saveAndValidatePost(title, content);
        getAndValidatePost(title, content, location);
        client.get()
                // 这里的ID放一个不存在的，这样http status才会返回404 NOT_FOUND
                .uri("/posts/800")
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    /**
     * Test update interface
     */
    @Test
    public void update() {
        String title = "Test Update interface";
        String content = "Content of Test Update interface";
        String url = "/posts/11";
        String newTitle = "new - Test Update interface";
        String newContent = "new - Content of Test Update interface";
//        WebTestClient.ResponseSpec spec =
                client.put()
                .uri("/posts/11")
                .body(BodyInserters.fromValue(
                        PostModel.builder()
                                .title(newTitle)
                                .content(newContent)
                                .createDate(LocalDateTime.now())
                                .id(11L)
                                .build()
                        )
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                ;
//        spec.expectStatus().
//        EntityExchangeResult<byte[]> getResult = client
//                .get()
//                .uri(url)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody()
//                .jsonPath("$.title")
//                .isEqualTo(title)
//                .jsonPath("$.content").isEqualTo(content)
//                .returnResult();
//        getAndValidatePost(title, content, url);
    }

    /**
     * Test delete interface
     */
    @Test
    public void delete() {
        String title = "Test Update interface";
        String content = "Content of Test Update interface";

        URI location = saveAndValidatePost(title, content);

        client.delete()
                .uri(location)
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri(location)
                .exchange()
                .expectStatus().isNotFound();
    }


    /**
     * Save and validate save post interface
     *
     * @param title   the post title
     * @param content the post content
     * @return the URI of saved post
     */
    private URI saveAndValidatePost(String title, String content) {
        FluxExchangeResult<Void> postResult = client.post()
                .uri("/posts")
                .body(BodyInserters.fromObject(
                        PostModel.builder()
                                .title(title)
                                .content(content)
                                .build()
                        )
                )
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(Void.class);
        URI location = postResult.getResponseHeaders().getLocation();
        assertNotNull("Result should not be null if create success", location);
        return location;
    }

    /**
     * Get and validate the post by URI
     *
     * @param title    the expected title
     * @param content  the expected content
     * @param location the URI of saved post
     * @return the post entity get by URI
     */
    private PostModel getAndValidatePost(String title, String content, URI location) {
        EntityExchangeResult<byte[]> getResult = client
                .get()
                .uri(location)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.title")
                .isEqualTo(title)
                .jsonPath("$.content").isEqualTo(content)
                .returnResult();
        assertNotNull("Result body should not be null", getResult);
        assertNotNull("Result body content should not be null", getResult.getResponseBody());
        String responseBodyStr = new String(getResult.getResponseBody());
        logger.info("responseBodyStr ==== " + responseBodyStr);
        assertTrue("Result body should contains title", responseBodyStr.contains(title));
        return JSON.parseObject(responseBodyStr, PostModel.class);
    }


}
