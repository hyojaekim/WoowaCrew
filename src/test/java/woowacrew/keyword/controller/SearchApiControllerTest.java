package woowacrew.keyword.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 정상적으로_구글에_검색한다() {
        webTestClient.get()
                .uri("/api/search?content=test")
                .exchange()
                .expectHeader()
                .value("Location", Matchers.containsString("google.com"));
    }
}