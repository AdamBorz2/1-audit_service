package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.model.request.AuditLogRequest;
import com.itm.space.auditservice.model.response.AuditLogResponse;
import com.itm.space.auditservice.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuditLogControllerImpl implements AuditLogController {

    private final AuditService auditService;

    @Override
    public ResponseEntity<AuditLogResponse> audit(AuditLogRequest request) {
        UUID auditId = auditService.createAuditLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuditLogResponse(auditId));
    }
}
