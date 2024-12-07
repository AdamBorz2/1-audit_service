package com.itm.space.auditservice.handler;

import com.itm.space.auditservice.exception.BadRequestException;
import com.itm.space.auditservice.exception.EntityAlreadyExistsException;
import com.itm.space.auditservice.exception.InternalServerErrorException;
import com.itm.space.auditservice.model.response.HttpErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.itm.space.auditservice.constant.ErrorsMessagesConstants.ENTITY_TYPE_NOT_ACCESS;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<HttpErrorResponse> handleException(EntityAlreadyExistsException e) {
        HttpStatus status = e.getHttpStatus();
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage()
        );
        return new ResponseEntity<>(httpErrorResponse, status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpErrorResponse> handleException(BadRequestException e) {
        HttpStatus status = e.getHttpStatus();
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage()
        );
        return new ResponseEntity<>(httpErrorResponse, status);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<HttpErrorResponse> handleException(InternalServerErrorException e) {
        HttpStatus status = e.getHttpStatus();
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage()
        );
        return new ResponseEntity<>(httpErrorResponse, status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpErrorResponse> handleException() {
        HttpStatus status = HttpStatus.FORBIDDEN;
        HttpErrorResponse error = new HttpErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ENTITY_TYPE_NOT_ACCESS
        );
        return new ResponseEntity<>(error, status);
    }
}