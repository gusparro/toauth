package com.gusparro.toauth.api.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime dateTime;

}
