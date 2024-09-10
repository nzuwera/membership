package com.nzuwera.membership.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nzuwera.membership.exception.AlreadyExistsException;
import com.nzuwera.membership.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {
    private boolean status;
    private String message;
    private Object data;
    private int errorCode;

    public ResponseObject(NotFoundException ex){
        this.status = false;
        this.message = ex.getMessage();
        this.data = ex;
        this.errorCode = 404;
    }

    public ResponseObject(AlreadyExistsException ex){
        this.status = false;
        this.message = ex.getMessage();
        this.errorCode = 409;
    }

    public ResponseObject(Exception ex){
        this.status = false;
        this.message = ex.getMessage();
        this.data = ex;
        this.errorCode = 500;
    }
}
