package com.itm.space.auditservice.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class EntityTypeResponse {

    private UUID id;
    private String name;
    private String description;
    private boolean enabled;
}