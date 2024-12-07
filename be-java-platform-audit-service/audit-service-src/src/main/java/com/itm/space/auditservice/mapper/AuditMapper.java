package com.itm.space.auditservice.mapper;

import com.itm.space.auditservice.domain.AuditLogEntity;
import com.itm.space.auditservice.domain.EntityType;
import com.itm.space.auditservice.domain.EventSource;
import com.itm.space.auditservice.domain.EventType;
import com.itm.space.auditservice.model.request.AuditLogRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    @Mapping(target = "eventDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "eventType", source = "eventType.id")
    @Mapping(target = "userId", source = "request.userId")
    @Mapping(target = "entityType", source = "entityType.id")
    @Mapping(target = "entityId", source = "request.entityId")
    @Mapping(target = "eventSource", source = "eventSource.id")
    @Mapping(target = "eventData", source = "request.eventData")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleteDate", ignore = true)
    AuditLogEntity toAuditLogEntity(AuditLogRequest request,
                                    EntityType entityType,
                                    EventSource eventSource,
                                    EventType eventType);

}
