package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.model.request.EntityTypeRequest;
import com.itm.space.auditservice.model.response.EntityTypeResponse;
import com.itm.space.auditservice.service.impl.EntityTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EntityTypeControllerImpl implements EntityTypeController {

    private final EntityTypeServiceImpl entityTypeServiceImpl;

    @Override
    public EntityTypeResponse createEntityType(EntityTypeRequest request) {
        return entityTypeServiceImpl.createEntityType(request);
    }
}