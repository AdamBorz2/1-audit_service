package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.BaseIntegrationTest;
import com.itm.space.auditservice.constant.ApiConstant;
import com.itm.space.auditservice.model.request.EntityTypeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

class EntityTypeControllerTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Тест на получение ответа - 200")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateEntityType_WhenRequestIsValid() {
        webTestClient.post()
                .uri(ApiConstant.ADMIN_ENTITY_TYPE_POST)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new EntityTypeRequest("BIRD", "События, связанные с птицами", true))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("BIRD")
                .jsonPath("$.description").isEqualTo("События, связанные с птицами")
                .jsonPath("$.enabled").isEqualTo(true);
    }

    @Test
    @DisplayName("Тест на получение ответа - 400")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnBadRequest_WhenRequestIsInvalid() {
        webTestClient.post()
                .uri(ApiConstant.ADMIN_ENTITY_TYPE_POST)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new EntityTypeRequest())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("400")
                .jsonPath("$.type").isEqualTo("Bad Request")
                .jsonPath("$.message").isEqualTo("Неправильные параметры запроса");
    }

    @Test
    @DisplayName("Тест на получение ответа - 401")
    void shouldReturnUnauthorized_WhenUserIsNotAuthenticated() {
        webTestClient.post()
                .uri(ApiConstant.ADMIN_ENTITY_TYPE_POST)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new EntityTypeRequest("USER", "События, связанные с пользователями", true))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("401")
                .jsonPath("$.type").isEqualTo("Unauthorized")
                .jsonPath("$.message").isEqualTo("Пользователь не аутентифицирован");
    }

    @Test
    @DisplayName("Тест на получение ответа - 403")
    @WithMockUser(roles = "USER")
    void shouldReturnForbidden_WhenUserLacksPermissions() {
        webTestClient.post()
                .uri(ApiConstant.ADMIN_ENTITY_TYPE_POST)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new EntityTypeRequest("USER", "События, связанные с пользователями", true))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("403")
                .jsonPath("$.type").isEqualTo("Forbidden")
                .jsonPath("$.message").isEqualTo("Недостаточно прав пользователя");
    }

    @Test
    @DisplayName("Тест на получение ответа - 409")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnConflict_WhenEntityTypeAlreadyExists() {
        webTestClient.post()
                .uri(ApiConstant.ADMIN_ENTITY_TYPE_POST)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new EntityTypeRequest("USER", "События, связанные с пользователями", true))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("409")
                .jsonPath("$.type").isEqualTo("Conflict")
                .jsonPath("$.message").isEqualTo("Данный тип сущности уже существует");
    }
}
