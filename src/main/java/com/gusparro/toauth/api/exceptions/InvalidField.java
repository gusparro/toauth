package com.gusparro.toauth.api.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvalidField {

    private String name;
    private String errorMessage;

}
