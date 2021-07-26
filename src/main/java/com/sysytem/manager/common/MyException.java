package com.sysytem.manager.common;

import lombok.Data;

/**
 * @author: wangzhi
 * @Title: MyException
 * @Description:
 * @date: 2021/7/23 19:49
 */
@Data
public class MyException extends RuntimeException{
    private ErrorCode errorCode;

    public MyException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.setErrorCode(errorCode);
    }
}
