package com.itm.space.auditservice.repository;

import com.itm.space.auditservice.domain.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType, UUID> {

    Optional<EntityType> findByName(String name);

    Optional<EntityType> findEntityTypeByName(String name);
}