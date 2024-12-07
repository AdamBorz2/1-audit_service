package com.itm.space.auditservice.util;

import com.itm.space.auditservice.exception.BadRequestException;
import com.itm.space.auditservice.model.request.EntityTypeRequest;
import org.springframework.stereotype.Component;

import static com.itm.space.auditservice.constant.ErrorsMessagesConstants.ENTITY_TYPE_BAD_REQUEST;

@Component
public class EntityTypeRequestValid {

    public void validate(EntityTypeRequest request) {

        if (request == null) {
            throw new BadRequestException(ENTITY_TYPE_BAD_REQUEST);
        }
        if (request.getName() == null || request.getName().isBlank() || request.getName().length() > 100) {
            throw new BadRequestException(ENTITY_TYPE_BAD_REQUEST);
        }
        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new BadRequestException(ENTITY_TYPE_BAD_REQUEST);
        }
    }
}
