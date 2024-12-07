package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.model.request.EntityTypeRequest;
import com.itm.space.auditservice.model.response.EntityTypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.auditservice.constant.ApiConstant.ADMIN_ENTITY_TYPE_POST;

@Tag(name = "EntityType Controller", description = "CRUD операции с транзакциями")
@RequestMapping(ADMIN_ENTITY_TYPE_POST)
public interface EntityTypeController {

    @Operation(summary = "Создание EntityType", description = "Создание типа сущности")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание типа сущности"),
            @ApiResponse(responseCode = "400", description = "Неправильные параметры запроса"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав пользователя"),
            @ApiResponse(responseCode = "409", description = "Данный тип сущности уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping()
    @Secured("ROLE_ADMIN")
    EntityTypeResponse createEntityType(@RequestBody @Valid EntityTypeRequest request);
}