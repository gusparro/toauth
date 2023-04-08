package com.gusparro.toauth.domain.exceptions.role;

import com.gusparro.toauth.domain.exceptions.EntityInUseException;

public class RoleInUseException extends EntityInUseException {

    private static final String ROLE_IN_USE_MESSAGE = "Role cannot be removed, as it is in use.";

    public RoleInUseException() {
        super(ROLE_IN_USE_MESSAGE);
    }

    public RoleInUseException(String message) {
        super(message);
    }

}
