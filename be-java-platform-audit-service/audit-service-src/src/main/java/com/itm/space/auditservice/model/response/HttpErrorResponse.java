package com.itm.space.auditservice.model.response;

public record HttpErrorResponse(int code, String type, String message) {
}
