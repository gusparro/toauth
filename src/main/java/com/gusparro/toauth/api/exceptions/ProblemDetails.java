package com.gusparro.toauth.api.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class ProblemDetails {

    private int status;
    private String type;
    private String title;
    private String detail;

}
