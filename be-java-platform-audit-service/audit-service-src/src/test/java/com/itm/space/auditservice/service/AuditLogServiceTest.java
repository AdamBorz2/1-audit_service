package com.itm.space.auditservice.service;

import com.itm.space.auditservice.BaseUnitTest;
import com.itm.space.auditservice.domain.AuditLogEntity;
import com.itm.space.auditservice.domain.EntityType;
import com.itm.space.auditservice.domain.EventSource;
import com.itm.space.auditservice.domain.EventType;
import com.itm.space.auditservice.domain.SourceType;
import com.itm.space.auditservice.mapper.AuditMapper;
import com.itm.space.auditservice.model.request.AuditLogRequest;
import com.itm.space.auditservice.repository.AuditLogRepository;
import com.itm.space.auditservice.repository.EntityTypeRepository;
import com.itm.space.auditservice.repository.EventSourceRepository;
import com.itm.space.auditservice.repository.EventTypeRepository;
import com.itm.space.auditservice.repository.SourceTypeRepository;
import com.itm.space.auditservice.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuditLogServiceTest extends BaseUnitTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private EntityTypeRepository entityTypeRepository;

    @Mock
    private SourceTypeRepository sourceTypeRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private EventSourceRepository eventSourceRepository;

    @Mock
    private AuditMapper auditMapper;

    @InjectMocks
    private AuditServiceImpl auditService;

    @Test
    @DisplayName("Успешное сохранение журнала аудита")
    void shouldCreateAuditLogSuccessfully() {

        UUID expectedUUID = UUID.randomUUID();
        UUID sourceUUID = UUID.randomUUID();

        AuditLogRequest request = mock(AuditLogRequest.class);
        EntityType entityType = mock(EntityType.class);
        EventType eventType = mock(EventType.class);
        SourceType sourceType = mock(SourceType.class);
        EventSource eventSource = mock(EventSource.class);
        AuditLogEntity auditLogEntity = new AuditLogEntity();
        auditLogEntity.setId(expectedUUID);
        auditLogEntity.setUserId(UUID.randomUUID());
        auditLogEntity.setEntityType(UUID.randomUUID());
        auditLogEntity.setEventType(UUID.randomUUID());
        auditLogEntity.setEntityId("123");
        auditLogEntity.setEventSource(sourceUUID);
        auditLogEntity.setEventType(eventType.getId());
        auditLogEntity.setEventData("{\"id\":\"d3e8d99a-c06a-43e6-b525-822b0cc415db\",\"type\":\"CREATED\",\"roles\":[],\"groups\":[],\"directionId\":\"da686dce-4947-489f-943c-0525f9518084\",\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"email\":\"ivan.ivanov@mail.ru\",\"isArchived\":\"false\"}");

        when(request.getEntityType()).thenReturn("USER");
        when(entityTypeRepository.findByName("USER")).thenReturn(Optional.of(entityType));

        when(request.getEventType()).thenReturn("CREATE");
        when(eventTypeRepository.findByName("CREATE")).thenReturn(Optional.of(eventType));

        when(request.getSourceType()).thenReturn("MANUAL");
        when(sourceTypeRepository.findByName("MANUAL")).thenReturn(Optional.of(sourceType));

        when(sourceType.getId()).thenReturn(sourceUUID);

        when(eventSourceRepository.findBySourceType(sourceUUID)).thenReturn(Optional.of(eventSource));

        when(auditMapper.toAuditLogEntity(request, entityType, eventSource, eventType)).thenReturn(auditLogEntity);
        when(auditLogRepository.save(any(AuditLogEntity.class))).thenReturn(auditLogEntity);

        UUID result = auditService.createAuditLog(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, expectedUUID);

        verify(auditLogRepository).save(any(AuditLogEntity.class));
    }

    @Test
    @DisplayName("Не успешное сохранение журнала аудита")
    void shouldThrowExceptionWhenAuditLogSaveFails() {

        UUID sourceUUID = UUID.randomUUID();

        AuditLogRequest request = mock(AuditLogRequest.class);
        EntityType entityType = mock(EntityType.class);
        EventType eventType = mock(EventType.class);
        SourceType sourceType = mock(SourceType.class);
        EventSource eventSource = mock(EventSource.class);
        AuditLogEntity auditLogEntity = new AuditLogEntity();

        when(request.getEntityType()).thenReturn("USER");
        when(entityTypeRepository.findByName("USER")).thenReturn(Optional.of(entityType));

        when(request.getEventType()).thenReturn("CREATE");
        when(eventTypeRepository.findByName("CREATE")).thenReturn(Optional.of(eventType));

        when(request.getSourceType()).thenReturn("MANUAL");
        when(sourceTypeRepository.findByName("MANUAL")).thenReturn(Optional.of(sourceType));

        when(sourceType.getId()).thenReturn(sourceUUID);
        when(eventSourceRepository.findBySourceType(sourceUUID)).thenReturn(Optional.of(eventSource));

        when(auditMapper.toAuditLogEntity(request, entityType, eventSource, eventType)).thenReturn(auditLogEntity);

        when(auditLogRepository.save(any(AuditLogEntity.class)))
                .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> auditService.createAuditLog(request)
        );

        Assertions.assertEquals("Database error", exception.getMessage());
        verify(auditLogRepository).save(any(AuditLogEntity.class));
    }
}
