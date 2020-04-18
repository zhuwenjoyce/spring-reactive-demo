package com.joyce;

/**
 * @author: Joyce Zhu
 * @date: 2020/4/14
 */

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

//@ExtendWith(SpringExtension.class)                  
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
//@SpringBootTest(classes = SpringReactiveDemoApplication.class)
public class GreetingRouterTest {

    // Spring Boot will create a `WebTestClient` for you,
    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testHello() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/hello")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello, Spring!");
    }
}
