package com.itm.space.auditservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogRequest {

    @NotBlank
    private String eventType;

    @NotNull
    private UUID userId;

    @NotBlank
    private String entityType;

    @NotBlank
    @Size(max = 255)
    private String entityId;

    @NotBlank
    @JsonProperty("event_data")
    private String eventData;

    @NotBlank
    private String sourceType;
}
