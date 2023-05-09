package com.gusparro.toauth.domain.exceptions.appuser;

import com.gusparro.toauth.domain.exceptions.EntityInUseException;

public class AppUserDuplicateKeyException extends EntityInUseException {

    public AppUserDuplicateKeyException(String message) {
        super(message);
    }

}
