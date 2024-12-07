package com.itm.space.auditservice.service.impl;

import com.itm.space.auditservice.domain.EntityType;
import com.itm.space.auditservice.exception.EntityAlreadyExistsException;
import com.itm.space.auditservice.mapper.EntityTypeMapper;
import com.itm.space.auditservice.model.request.EntityTypeRequest;
import com.itm.space.auditservice.model.response.EntityTypeResponse;
import com.itm.space.auditservice.repository.EntityTypeRepository;
import com.itm.space.auditservice.service.EntityTypeService;
import com.itm.space.auditservice.util.EntityTypeRequestValid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.itm.space.auditservice.constant.ErrorsMessagesConstants.ENTITY_TYPE_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class EntityTypeServiceImpl implements EntityTypeService {

    private final EntityTypeRepository entityTypeRepository;
    private final EntityTypeRequestValid entityTypeRequestValid;
    private final EntityTypeMapper entityTypeMapper;

    @Transactional
    @Override
    public EntityTypeResponse createEntityType(EntityTypeRequest request) {

        entityTypeRepository.findEntityTypeByName(request.getName()).ifPresent(entity -> {
            throw new EntityAlreadyExistsException(ENTITY_TYPE_ALREADY_EXISTS);
        });
        entityTypeRequestValid.validate(request);

        EntityType entityType = entityTypeMapper.requestToEntityType(request);

        entityTypeRepository.save(entityType);

        return entityTypeMapper.entityTypeToResponse(entityType);
    }
}
