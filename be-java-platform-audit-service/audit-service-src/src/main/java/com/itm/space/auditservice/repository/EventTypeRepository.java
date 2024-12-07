package com.itm.space.auditservice.repository;

import com.itm.space.auditservice.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {

    Optional<EventType> findByName(String name);

}