package com.nzuwera.membership.exception;

import com.nzuwera.membership.message.Message;

public class NotFoundException extends Exception {
    private final String errorString;

    public NotFoundException(String errorMessage) {
        this.errorString = String.format(Message.NOT_FOUND, errorMessage);
    }

    @Override
    public String getMessage() {
        return this.errorString;
    }


}
