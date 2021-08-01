package com.system.manager.common;

import lombok.Data;

/**
 * @author: wangzhi
 * @Title: MyException
 * @Description:
 * @date: 2021/7/23 19:49
 */
@Data
public class MyException extends RuntimeException{
    private final static Integer errorCode = 500;
    private Integer code;
    private String message;

    public MyException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.setCode(errorCode.getCode());
        this.setMessage(errorCode.getMessage());
    }

    public MyException(Integer code, String message){
        super(message);
        this.setCode(code);
        this.setMessage(message);
    }
    public MyException(String message){
        super(message);
        this.setCode(errorCode);
        this.setMessage(message);
    }
}
