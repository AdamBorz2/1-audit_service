package com.itm.space.auditservice.repository;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.itm.space.auditservice.BaseIntegrationTest;
import com.itm.space.auditservice.domain.AuditLogEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AuditLogRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Test
    @DisplayName("Тест на сохранение сущности")
    @DataSet(value =
            {"dataset/entity/EventType.yml",
                    "dataset/entity/EntityType.yml",
                    "dataset/entity/EventSource.yml",
                    "dataset/entity/SourceType.yml"}, cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "dataset/repository/AuditLogEntityExpected.yml", ignoreCols = {"id", "create_date", "event_data", "event_date"})
    public void saveAuditLogTest() {

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setEventDate(LocalDateTime.parse("2023-10-01T10:15:30"));
        auditLog.setEventType(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        auditLog.setUserId(UUID.fromString("987e6543-e21b-45d6-b123-456789abcdef"));
        auditLog.setEntityType(UUID.fromString("234e5678-e12b-34d5-a678-426614174001"));
        auditLog.setEntityId("entity-123");
        auditLog.setEventSource(UUID.fromString("345e6789-e23b-56d7-a789-426614174002"));
        auditLog.setEventData("{\"name\": \"test\"}");

        UUID id = auditLogRepository.save(auditLog).getId();

        AuditLogEntity savedAuditLog = auditLogRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        assertEquals(savedAuditLog.getEventDate(), auditLog.getEventDate());
        assertEquals(savedAuditLog.getEventData(), auditLog.getEventData());
        assertNotNull(savedAuditLog.getCreateDate());
        assertNotNull(savedAuditLog.getEntityTypeEntity());
        assertNotNull(savedAuditLog.getEventSourceEntity());
        assertNotNull(savedAuditLog.getEventTypeEntity());

    }

    @Test
    @DisplayName("Тест на изменение сущности")
    @DataSet(value = {"dataset/entity/EventType.yml",
            "dataset/entity/EntityType.yml",
            "dataset/entity/EventSource.yml",
            "dataset/entity/SourceType.yml"}, cleanBefore = true, cleanAfter = true)
    public void changeAuditLogTest() {

        AuditLogEntity initialAuditLog = new AuditLogEntity();
        initialAuditLog.setEventDate(LocalDateTime.parse("2023-10-01T10:15:30"));
        initialAuditLog.setEventType(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        initialAuditLog.setUserId(UUID.fromString("987e6543-e21b-45d6-b123-456789abcdef"));
        initialAuditLog.setEntityType(UUID.fromString("234e5678-e12b-34d5-a678-426614174001"));
        initialAuditLog.setEntityId("entity-123");
        initialAuditLog.setEventSource(UUID.fromString("345e6789-e23b-56d7-a789-426614174002"));
        initialAuditLog.setEventData("{\"name\": \"test\"}");

        UUID id = auditLogRepository.save(initialAuditLog).getId();

        AuditLogEntity changedAuditLog = auditLogRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        changedAuditLog.setDeleteDate(LocalDateTime.parse("2023-10-01T10:15:30"));
        changedAuditLog.setEntityType(UUID.fromString("333e5678-e12b-34d5-a678-426614174001"));
        changedAuditLog.setEntityId("new id");
        changedAuditLog.setEventData("{\"name\": \"admin\"}");

        auditLogRepository.save(changedAuditLog);

        AuditLogEntity savedAuditLog = auditLogRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        assertNotEquals(savedAuditLog.getEntityTypeEntity(), changedAuditLog.getEntityTypeEntity());
        assertEquals(savedAuditLog.getDeleteDate(), changedAuditLog.getDeleteDate());
        assertEquals(savedAuditLog.getEventDate(), changedAuditLog.getEventDate());
        assertEquals(savedAuditLog.getEventData(), changedAuditLog.getEventData());
        assertNotNull(savedAuditLog.getCreateDate());
        assertNotNull(savedAuditLog.getEntityTypeEntity());
        assertNotNull(savedAuditLog.getEventSourceEntity());
        assertNotNull(savedAuditLog.getEventTypeEntity());

    }

}



