package com.nzuwera.membership.exception;

import com.nzuwera.membership.message.Message;

public class AlreadyExistsException extends Exception {
    private final String errorString;

    public AlreadyExistsException(String errorMessage) {
        this.errorString = String.format(Message.ALREADY_EXISTS, errorMessage);
    }

    @Override
    public String getMessage() {
        return this.errorString;
    }
}
