package com.gusparro.toauth.domain.exceptions.appuser;

import com.gusparro.toauth.domain.exceptions.EntityNotFoundException;

public class AppUserNotFoundException extends EntityNotFoundException {

    private static final String USER_NOT_FOUND_MESSAGE = "User does not exist.";

    public AppUserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }

    public AppUserNotFoundException(String message) {
        super(message);
    }

}
