package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.BaseIntegrationTest;
import com.itm.space.auditservice.constant.ApiConstant;
import com.itm.space.auditservice.model.request.ExampleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

class ExampleControllerIT extends BaseIntegrationTest {

    @Test
    @DisplayName("Тест на получение ответа - 200")
    @WithMockUser
    void exampleRequestTest() {
        webTestClient.post()
                .uri(ApiConstant.EXAMPLE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new ExampleRequest("test"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Example response: test");
    }
}
