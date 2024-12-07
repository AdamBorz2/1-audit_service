package com.itm.space.auditservice.mapper;

import com.itm.space.auditservice.domain.EntityType;
import com.itm.space.auditservice.model.request.EntityTypeRequest;
import com.itm.space.auditservice.model.response.EntityTypeResponse;
import org.mapstruct.Mapper;

@Mapper
public interface EntityTypeMapper {

    EntityType requestToEntityType(EntityTypeRequest request);

    EntityTypeResponse entityTypeToResponse(EntityType entity);
}
