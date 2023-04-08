package com.gusparro.toauth.domain.exceptions.role;

import com.gusparro.toauth.domain.exceptions.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {

    private static final String ROLE_NOT_FOUND_MESSAGE = "Role does not exist.";

    public RoleNotFoundException() {
        super(ROLE_NOT_FOUND_MESSAGE);
    }

    public RoleNotFoundException(String message) {
        super(message);
    }

}
