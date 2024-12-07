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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_source")
public class EventSource extends BaseEntity {

    @Column(name = "source_type")
    private UUID sourceType;

    @NotNull
    @NotBlank
    @Size(max = 255)
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @ManyToOne
    @JoinColumn(name = "source_type", referencedColumnName = "id", insertable = false, updatable = false)
    private SourceType sourceTypeEntity;

}