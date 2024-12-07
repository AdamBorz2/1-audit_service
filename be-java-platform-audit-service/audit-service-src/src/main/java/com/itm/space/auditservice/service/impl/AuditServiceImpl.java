package com.itm.space.auditservice.service.impl;

import com.itm.space.auditservice.domain.AuditLogEntity;
import com.itm.space.auditservice.domain.EntityType;
import com.itm.space.auditservice.domain.EventSource;
import com.itm.space.auditservice.domain.EventType;
import com.itm.space.auditservice.domain.SourceType;
import com.itm.space.auditservice.exception.BadRequestException;
import com.itm.space.auditservice.mapper.AuditMapper;
import com.itm.space.auditservice.model.request.AuditLogRequest;
import com.itm.space.auditservice.repository.AuditLogRepository;
import com.itm.space.auditservice.repository.EntityTypeRepository;
import com.itm.space.auditservice.repository.EventSourceRepository;
import com.itm.space.auditservice.repository.EventTypeRepository;
import com.itm.space.auditservice.repository.SourceTypeRepository;
import com.itm.space.auditservice.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final EntityTypeRepository entityTypeRepository;
    private final SourceTypeRepository sourceTypeRepository;
    private final EventTypeRepository eventTypeRepository;
    private final EventSourceRepository eventSourceRepository;
    private final AuditMapper auditMapper;

    @Override
    @Transactional
    public UUID createAuditLog(AuditLogRequest request) {

        EventType eventType = eventTypeRepository.findByName(request.getEventType())
                .orElseThrow(() -> new BadRequestException("Тип события не найден"));

        EntityType entityType = entityTypeRepository.findByName(request.getEntityType())
                .orElseThrow(() -> new BadRequestException("Тип сущности не найден"));

        SourceType sourceType = sourceTypeRepository.findByName(request.getSourceType())
                .orElseThrow(() -> new BadRequestException("тип источника не найден"));

        EventSource eventSource = eventSourceRepository.findBySourceType(sourceType.getId())
                .orElseThrow(() -> new BadRequestException("Источник события не найден для идентификатора sourceType: " + sourceType.getId()));

        AuditLogEntity auditLogMapper = auditMapper.toAuditLogEntity(request, entityType, eventSource, eventType);

        AuditLogEntity auditLogEntity = auditLogRepository.save(auditLogMapper);

        return auditLogEntity.getId();
    }
}
