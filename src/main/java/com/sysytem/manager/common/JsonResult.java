package com.sysytem.manager.common;

import java.io.Serializable;

/**
 * @author: wangzhi
 * @Title: JsonResult
 * @Description:
 * @date: 2021/7/23 19:39
 */
public class JsonResult implements Serializable {
    private Integer success;
    public JsonResult(){
        this.success=0;
    }
}
