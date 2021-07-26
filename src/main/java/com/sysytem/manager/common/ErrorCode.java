package com.sysytem.manager.common;

import lombok.Data;

/**
 * @author: wangzhi
 * @Title: ErrorCode
 * @Description:
 * @date: 2021/7/23 19:44
 */
public enum ErrorCode {

    PRODUCT_VIDEO_SIZE_LIMIT_ERROR(801006, "数量超过限制")
    ;

    private Integer code;
    private String message;

    private ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
