package com.itm.space.auditservice.repository;

import com.itm.space.auditservice.domain.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SourceTypeRepository extends JpaRepository<SourceType, UUID> {

    Optional<SourceType> findByName(String name);

}


