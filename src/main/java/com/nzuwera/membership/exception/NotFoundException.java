package com.nzuwera.membership.exception;

import com.nzuwera.membership.message.Message;

public class NotFoundException extends Exception {
    private String name;
    private String message;

    public NotFoundException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        message = String.format(Message.NOT_FOUND, name);
        return message;
    }
}
