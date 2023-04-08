package com.gusparro.toauth.api.exceptions.handler;

import com.gusparro.toauth.api.exceptions.ProblemDetails;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserInUseException;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserNotFoundException;
import com.gusparro.toauth.domain.exceptions.role.RoleNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<?> handleAppUserNotFoundException(AppUserNotFoundException exception) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .status(404)
                .title("AppUser does not exist.")
                .detail(exception.getMessage())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(AppUserInUseException.class)
    public ResponseEntity<?> handleAppUserInUseException(AppUserInUseException exception) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .status(409)
                .title("AppUser in use.")
                .detail(exception.getMessage())
                .build();

        return ResponseEntity.status(CONFLICT).body(problemDetails);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException exception) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .status(400)
                .title("Role does not exist.")
                .detail(exception.getMessage())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(problemDetails);
    }

}
