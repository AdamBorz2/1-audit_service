package com.itm.space.auditservice.service;

import com.itm.space.auditservice.model.request.EntityTypeRequest;
import com.itm.space.auditservice.model.response.EntityTypeResponse;

public interface EntityTypeService {
    EntityTypeResponse createEntityType(EntityTypeRequest request);
}
