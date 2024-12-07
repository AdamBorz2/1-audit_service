package com.itm.space.auditservice.repository;

import com.itm.space.auditservice.domain.EventSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventSourceRepository extends JpaRepository<EventSource, UUID> {

    Optional<EventSource> findBySourceType(UUID sourceTypeId);

}
