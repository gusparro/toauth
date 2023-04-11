package com.gusparro.toauth.api.exceptions.handler;

import com.gusparro.toauth.api.exceptions.InvalidField;
import com.gusparro.toauth.api.exceptions.ProblemDetails;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserInUseException;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserNotFoundException;
import com.gusparro.toauth.domain.exceptions.role.RoleNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        BindingResult bindingResult = exception.getBindingResult();
        List<InvalidField> fields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> InvalidField.builder()
                        .name(fieldError.getField())
                        .errorMessage(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ProblemDetails problemDetails = ProblemDetails.builder()
                .status(400)
                .title("Invalid Fields")
                .detail("One or more fields are invalid, fill in the correct form and try again.")
                .fields(fields)
                .build();


        return handleExceptionInternal(exception, problemDetails, headers, status, request);
    }
}
