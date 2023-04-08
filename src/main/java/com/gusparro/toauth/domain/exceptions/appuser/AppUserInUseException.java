package com.gusparro.toauth.domain.exceptions.appuser;

import com.gusparro.toauth.domain.exceptions.EntityInUseException;

public class AppUserInUseException extends EntityInUseException {

    private static final String USER_IN_USE_MESSAGE = "User cannot be removed, as it is in use.";

    public AppUserInUseException() {
        super(USER_IN_USE_MESSAGE);
    }

    public AppUserInUseException(String message) {
        super(message);
    }

}
