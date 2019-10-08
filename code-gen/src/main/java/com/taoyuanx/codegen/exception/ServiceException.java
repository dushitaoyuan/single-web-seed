package com.taoyuanx.codegen.exception;


import com.taoyuanx.commons.api.ResultCode;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 8793672380339632040L;
    private Integer errorCode;

    public ServiceException(String msg) {
        super(msg);
        this.errorCode = ResultCode.BUSSINESS_ERROR.code;
    }

    public ServiceException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;

    }

    public  Integer getErrorCode(){
        return  errorCode;
    }


}
