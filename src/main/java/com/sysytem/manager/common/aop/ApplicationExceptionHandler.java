package com.sysytem.manager.common.aop;

import com.alibaba.fastjson.JSON;
import com.sysytem.manager.common.ErrorCode;
import com.sysytem.manager.common.MyException;
import com.sysytem.manager.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author: wangzhi
 * @Title: ApplicationExceptionHandler
 * @Description:
 * @date: 2021/7/28 16:45
 */
@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex){
        HashMap<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("ip", request.getRemoteAddr());
        logMap.put("method", request.getMethod());
        logMap.put("url", request.getRequestURL().toString());
        if (ex instanceof MyException) {
            logMap.put("error_code", ((MyException) ex).getCode());
            logMap.put("message", ex.getMessage());
            log.error(JSON.toJSONString(logMap));
            return Result.error(((MyException) ex).getCode(),ex.getMessage());
        } else {
            logMap.put("error_code", ErrorCode.SYSTEM_EXCEPTION.getCode());
            logMap.put("message", ex.getMessage());
            log.error(JSON.toJSONString(logMap),ex);
            return Result.error();
        }
    }
}
