package com.gusparro.toauth.domain.exceptions;

public abstract class EntityInUseException extends RuntimeException {

    public EntityInUseException(String message) {
        super(message);
    }

}
