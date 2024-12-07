package com.itm.space.auditservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerErrorException extends PlatformException {
    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}