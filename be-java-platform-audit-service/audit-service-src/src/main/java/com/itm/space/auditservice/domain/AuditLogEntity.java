package com.itm.space.auditservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_log")
public final class AuditLogEntity extends BaseEntity {

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @NotNull
    @Column(name = "event_type", nullable = false)
    private UUID eventType;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "entity_type", nullable = false)
    private UUID entityType;

    @Size(max = 255)
    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "event_source", nullable = false)
    private UUID eventSource;

    @NotBlank
    @Column(name = "event_data", columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String eventData;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @ManyToOne
    @JoinColumn(name = "event_type", referencedColumnName = "id", insertable = false, updatable = false)
    private EventType eventTypeEntity;

    @ManyToOne
    @JoinColumn(name = "entity_type", referencedColumnName = "id", insertable = false, updatable = false)
    private EntityType entityTypeEntity;

    @ManyToOne
    @JoinColumn(name = "event_source", referencedColumnName = "id", insertable = false, updatable = false)
    private EventSource eventSourceEntity;

}

