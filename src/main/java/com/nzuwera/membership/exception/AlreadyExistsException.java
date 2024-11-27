package com.nzuwera.membership.exception;

import com.nzuwera.membership.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {
    private final String errorString;

    public AlreadyExistsException(String errorMessage) {
        this.errorString = String.format(Message.ALREADY_EXISTS, errorMessage);
    }

    @Override
    public String getMessage() {
        return this.errorString;
    }
}
