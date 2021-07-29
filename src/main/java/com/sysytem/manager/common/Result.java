package com.sysytem.manager.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 公共响应信息 Copyright: Copyright (C) 2018-2028, Inc. All rights reserved. Company: 中兴新云.财务云
 * 
 * @author zengj
 * @date 2018年11月9日
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    private static final Integer ERROR_CODE = 500;
    /**
     * 默认错误信息
     */
    private static final String ERROR_MSG = "系统错误";


    /** * 常量：code */
    public static final String CODE = "code";

    /** * 常量:msg */
    public static final String MSG = "msg";

    /** * 常量:消息类型 */
    public static final String TYPE = "type";

    /** * 功能类型 */
    public static final String FUN_TYPE = "funType";

    /** * 常量:消息类型 */
    public static enum MSG_TYPE {
        /** * 错误消息 */
        ERROR,
        /** * 提示消息 */
        INFO,
        /** * 确认消息 */
        WARNING;
    }


    /** * 成功对应代码 */
    public static final int SUCCESS_CODE = 0;

    /** * 警告对应代码 */
    public static final int WRANING_CODE = 2000;

    /** * 补充信息代码 */
    public static final int SUPPLY_CODE = 3000;

    /** * 成功对应消息 */
    public static final String SUCCESS_MSG = "success";

    public Result() {
        put(CODE, SUCCESS_CODE);
        put(MSG, SUCCESS_MSG);
        put(TYPE, MSG_TYPE.INFO);
    }

    /**
     * 默认错误提示
     * @return
     */
    public static Result error() {
        Result r = new Result();
        r.put(CODE,ERROR_CODE);
        r.put(MSG,ERROR_MSG);
        return r;
    }


    /**
     * 警告
     * 
     * @param msg 消息
     * @return
     */
    public static Result warn(String msg) {
        return warn(WRANING_CODE, msg);
    }


    public static Result errorMsg(String errMsg) {
        return error(errMsg);
    }

    public static Result error(String msg) {
        return error(ERROR_CODE, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put(CODE, code);
        r.put(MSG, msg);
        r.put(TYPE, MSG_TYPE.ERROR);
        return r;
    }

    public static Result warn(int code, String msg) {
        Result r = new Result();
        r.put(CODE, code);
        r.put(MSG, msg);
        r.put(TYPE, MSG_TYPE.WARNING);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put(MSG, msg);
        r.put(TYPE, MSG_TYPE.INFO);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Boolean isOk() {
        if (get(CODE) == null) {
            return false;
        }
        if (SUCCESS_CODE == (int)get(CODE)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isWarn() {
        if (get(CODE) == null) {
            return false;
        }
        if (WRANING_CODE == (int)get(CODE)) {
            return true;
        } else {
            return false;
        }
    }
}