package com.itm.space.auditservice.service;

import com.itm.space.auditservice.model.request.AuditLogRequest;

import java.util.UUID;

public interface AuditService {

    UUID createAuditLog(AuditLogRequest request);

}
