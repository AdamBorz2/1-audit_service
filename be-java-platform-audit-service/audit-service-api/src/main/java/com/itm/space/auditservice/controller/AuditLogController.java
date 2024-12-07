package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.model.request.AuditLogRequest;
import com.itm.space.auditservice.model.response.AuditLogResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.auditservice.constant.ApiConstant.AUDIT_LOG_URL;
import static com.itm.space.auditservice.constant.RoleConstants.ADMIN;
import static com.itm.space.auditservice.constant.RoleConstants.SERVICE;

@RequestMapping(AUDIT_LOG_URL)
public interface AuditLogController {

    @Operation(summary = "Создание записи аудита", description = "Создание новой записи в таблицу audit_log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Отзыв успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неправильные параметры запроса"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав пользователя"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    @Secured({ADMIN, SERVICE})
    ResponseEntity<AuditLogResponse> audit(@RequestBody @Valid AuditLogRequest auditLogRequest);
}
