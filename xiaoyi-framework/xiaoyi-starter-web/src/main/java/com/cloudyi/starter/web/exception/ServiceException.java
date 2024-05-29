package com.cloudyi.starter.web.exception;

import com.cloudyi.starter.web.handler.response.IResultCode;
import com.cloudyi.starter.web.handler.response.ResultCode;
import lombok.Getter;


public class ServiceException extends RuntimeException {

    @Getter
    private final IResultCode resultCode;

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }
}
