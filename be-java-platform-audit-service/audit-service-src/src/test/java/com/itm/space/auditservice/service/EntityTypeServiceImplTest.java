package com.itm.space.auditservice.service;

import com.itm.space.auditservice.BaseUnitTest;
import com.itm.space.auditservice.domain.EntityType;
import com.itm.space.auditservice.exception.BadRequestException;
import com.itm.space.auditservice.exception.EntityAlreadyExistsException;
import com.itm.space.auditservice.mapper.EntityTypeMapper;
import com.itm.space.auditservice.model.request.EntityTypeRequest;
import com.itm.space.auditservice.model.response.EntityTypeResponse;
import com.itm.space.auditservice.repository.EntityTypeRepository;
import com.itm.space.auditservice.service.impl.EntityTypeServiceImpl;
import com.itm.space.auditservice.util.EntityTypeRequestValid;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.itm.space.auditservice.constant.ErrorsMessagesConstants.ENTITY_TYPE_ALREADY_EXISTS;
import static com.itm.space.auditservice.constant.ErrorsMessagesConstants.ENTITY_TYPE_BAD_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class EntityTypeServiceImplTest extends BaseUnitTest {

    @Mock
    private EntityTypeRepository entityTypeRepository;

    @Mock
    private EntityTypeRequestValid entityTypeRequestValid;

    @Mock
    private EntityTypeMapper entityTypeMapper;

    @InjectMocks
    private EntityTypeServiceImpl entityTypeServiceImpl;

    @Test
    @DisplayName("Успешное создание EntityType")
    @SneakyThrows
    void createEntityType_Successful() {
        EntityTypeRequest request = jsonParserUtil.getObjectFromJson(
                "JSON/service/EntityTypeService/valid-entity-type-request.json",
                EntityTypeRequest.class
        );

        EntityType entityType = new EntityType();
        entityType.setName(request.getName());
        entityType.setDescription(request.getDescription());
        entityType.setEnabled(request.isEnabled());

        EntityType savedEntity = new EntityType();
        savedEntity.setName(request.getName());
        savedEntity.setDescription(request.getDescription());
        savedEntity.setEnabled(request.isEnabled());

        EntityTypeResponse response = new EntityTypeResponse();
        response.setId(savedEntity.getId());
        response.setName(savedEntity.getName());
        response.setDescription(savedEntity.getDescription());
        response.setEnabled(savedEntity.getEnabled());

        when(entityTypeRepository.findEntityTypeByName(request.getName())).thenReturn(Optional.empty());
        doNothing().when(entityTypeRequestValid).validate(request);
        when(entityTypeMapper.requestToEntityType(request)).thenReturn(entityType);
        when(entityTypeRepository.save(entityType)).thenReturn(savedEntity);
        when(entityTypeMapper.entityTypeToResponse(any(EntityType.class))).thenReturn(response);

        EntityTypeResponse actualResponse = entityTypeServiceImpl.createEntityType(request);

        assertNotNull(actualResponse);
        assertEquals(response.getId(), actualResponse.getId());
        assertEquals(response.getName(), actualResponse.getName());
        assertEquals(response.getDescription(), actualResponse.getDescription());
        assertEquals(response.isEnabled(), actualResponse.isEnabled());

        verify(entityTypeRepository).findEntityTypeByName(request.getName());
        verify(entityTypeRequestValid).validate(request);
        verify(entityTypeMapper).requestToEntityType(request);
        verify(entityTypeRepository).save(entityType);
        verify(entityTypeMapper).entityTypeToResponse(any(EntityType.class));
    }

    @Test
    @DisplayName("Создание уже существующего EntityType")
    @SneakyThrows
    void createEntityType_AlreadyExists() {
        EntityTypeRequest request = jsonParserUtil.getObjectFromJson(
                "JSON/service/EntityTypeService/existing-entity-type-request.json",
                EntityTypeRequest.class
        );

        EntityType existingEntity = new EntityType();
        existingEntity.setName(request.getName());

        when(entityTypeRepository.findEntityTypeByName(request.getName())).thenReturn(Optional.of(existingEntity));

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class,
                () -> entityTypeServiceImpl.createEntityType(request));

        assertEquals(ENTITY_TYPE_ALREADY_EXISTS, exception.getMessage());

        verify(entityTypeRepository).findEntityTypeByName(request.getName());
        verifyNoInteractions(entityTypeRequestValid);
        verifyNoInteractions(entityTypeMapper);
        verify(entityTypeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Тест на невалидный EntityType")
    @SneakyThrows
    void createEntityType_InvalidRequest() {
        EntityTypeRequest request = jsonParserUtil.getObjectFromJson(
                "JSON/service/EntityTypeService/invalid-entity-type-request.json",
                EntityTypeRequest.class
        );

        when(entityTypeRepository.findEntityTypeByName(request.getName())).thenReturn(Optional.empty());
        doThrow(new BadRequestException(ENTITY_TYPE_BAD_REQUEST)).when(entityTypeRequestValid).validate(request);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> entityTypeServiceImpl.createEntityType(request));

        assertEquals(ENTITY_TYPE_BAD_REQUEST, exception.getMessage());

        verify(entityTypeRepository).findEntityTypeByName(request.getName());
        verify(entityTypeRequestValid).validate(request);
        verifyNoMoreInteractions(entityTypeRepository);
        verifyNoInteractions(entityTypeMapper);
    }
}
