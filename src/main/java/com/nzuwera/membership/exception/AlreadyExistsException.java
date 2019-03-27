package com.nzuwera.membership.exception;

import com.nzuwera.membership.message.Message;

public class AlreadyExistsException extends Exception {
    private String name;
    private String message;

    public AlreadyExistsException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String getMessage() {
        message = String.format(Message.ALREADY_EXISTS, name);
        return message;
    }
}
