package com.itm.space.auditservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityTypeRequest {

    @NotBlank(message = "Имя сущности не должно быть пустым")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Описание сущности не должно быть пустым")
    private String description;

    private boolean enabled;
}