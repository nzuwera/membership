package com.nzuwera.membership.utils;

import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;

public class ResponseObject {
    private boolean status;
    private String message;
    private Object data;
    private int errorCode;

    public ResponseObject() {
    }

    public ResponseObject(boolean status, String message, Object data, int errorCode) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
    }

    public ResponseObject(NotFoundException ex){
        this.status = false;
        this.message = ex.getMessage();
        this.data = ex;
        this.errorCode = 404;
    }

    public ResponseObject(AlreadyExistsException ex){
        this.status = false;
        this.message = ex.getMessage();
        this.data = ex;
        this.errorCode = 409;
    }

    public ResponseObject(Exception ex){
        this.status = false;
        this.message = ex.getMessage();
        this.data = ex;
        this.errorCode = 500;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
