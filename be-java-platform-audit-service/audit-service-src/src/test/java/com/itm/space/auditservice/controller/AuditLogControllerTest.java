package com.itm.space.auditservice.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.itm.space.auditservice.BaseIntegrationTest;
import com.itm.space.auditservice.model.request.AuditLogRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static com.itm.space.auditservice.constant.ApiConstant.AUDIT_LOG_URL;

class AuditLogControllerTest extends BaseIntegrationTest {

    private AuditLogRequest auditLogRequest = new AuditLogRequest(
            "CREATE",
            UUID.fromString("87e4d161-4379-4454-aa32-ac04b785aaee"),
            "USER",
            "d3e8d99a-c06a-43e6-b525-822b0cc415db",
            "{\"id\":\"d3e8d99a-c06a-43e6-b525-822b0cc415db\",\"type\":\"CREATED\",\"roles\":[],\"groups\":[],\"directionId\":\"da686dce-4947-489f-943c-0525f9518084\",\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"email\":\"ivan.ivanov@mail.ru\",\"isArchived\":\"false\"}",
            "MANUAL"
    );

    @Test
    @DisplayName("Успешное сохранение журнала аудита - 201")
    @DataSet(value = {"dataset/entity/EventSource.yml",
            "dataset/entity/SourceType.yml",
            "dataset/entity/EntityType.yml",
            "dataset/entity/EventType.yml"}
    )
    @WithMockUser(roles = "ADMIN")
    void auditShouldCreateAuditLog() {

        webTestClient.post()
                .uri(AUDIT_LOG_URL)
                .header(HttpHeaders.AUTHORIZATION, authUtil.getAuthorization("admin@admin.ru"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(auditLogRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    @DisplayName("пользователь c недостаточным правом доступа- 403")
    @WithMockUser(roles = "MENTOR")
    void shouldReturn403WhenNotEnoughRights() {

        webTestClient.post()
                .uri(AUDIT_LOG_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(auditLogRequest)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("403")
                .jsonPath("$.type").isEqualTo("Forbidden")
                .jsonPath("$.message").isEqualTo("Недостаточно прав пользователя");
    }

    @Test
    @DisplayName("Пользователь не аутентифицирован - 401")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUnauthorizedForInvalidToken() {

        String invalidToken = "Bearer invalid_token";

        webTestClient.post()
                .uri(AUDIT_LOG_URL)
                .header(HttpHeaders.AUTHORIZATION, invalidToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(auditLogRequest)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("401")
                .jsonPath("$.type").isEqualTo("Unauthorized")
                .jsonPath("$.message").isEqualTo("Пользователь не аутентифицирован");
    }

    @Test
    @DisplayName("Неправильный параметр запроса журнала аудита - 400")
    @DataSet(value = {"dataset/entity/EventSource.yml",
            "dataset/entity/SourceType.yml",
            "dataset/entity/EntityType.yml",
            "dataset/entity/EventType.yml"
    }
    )
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenRequestIsInvalid() {
        AuditLogRequest auditLogRequest1 = new AuditLogRequest(
                "CREATE",
                UUID.fromString("87e4d161-4379-4454-aa32-ac04b785aaee"),
                "FR",
                "d3e8d99a-c06a-43e6-b525-822b0cc415db",
                "{\"id\":\"d3e8d99a-c06a-43e6-b525-822b0cc415db\",\"type\":\"CREATED\",\"roles\":[],\"groups\":[],\"directionId\":\"da686dce-4947-489f-943c-0525f9518084\",\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"email\":\"ivan.ivanov@mail.ru\",\"isArchived\":\"false\"}",
                "MANUAL"
        );
        webTestClient.post()
                .uri(AUDIT_LOG_URL)
                .header(HttpHeaders.AUTHORIZATION, authUtil.getAuthorization("admin@admin.ru"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(auditLogRequest1)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("400")
                .jsonPath("$.type").isEqualTo("Bad Request");
    }
}
