package com.gusparro.toauth.api.exceptions.handler;

import com.gusparro.toauth.api.exceptions.ErrorResponse;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserInUseException;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserNotFoundException;
import com.gusparro.toauth.domain.exceptions.role.RoleNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<?> handleAppUserNotFoundException(AppUserNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(404)
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AppUserInUseException.class)
    public ResponseEntity<?> handleAppUserInUseException(AppUserInUseException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(409)
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(400)
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

}
