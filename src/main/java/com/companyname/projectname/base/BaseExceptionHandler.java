package com.companyname.projectname.base;

import com.companyname.projectname.entity.dto.BaseOutDTO;
import com.companyname.projectname.util.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局统一异常处理
 *
 * @author renbaojie
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseOutDTO defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("接口: {} 请求失败", request.getRequestURI());
        log.error("异常信息: ", e);
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return new BaseOutDTO().fail(new BaseError().nextError("接口不支持:" + request.getMethod() + " 请求方式"));
        }

        // 出现异常时，设置本次请求id到响应头中。（后端可以快速定位到是哪次请求异常）
        response.addHeader(TraceIdUtil.TRACE_ID, TraceIdUtil.getTraceId());
        return new BaseOutDTO().fail(BaseError.SYS_ERR);
    }

    @ExceptionHandler(value = BaseRuntimeException.class)
    @ResponseBody
    public BaseOutDTO knownErrorHandler(HttpServletRequest request, HttpServletResponse response, BaseRuntimeException e) {
        log.error("接口: {} 请求失败 : {}", request.getRequestURI(), e.getMessage());
        // 出现异常时，设置本次请求id到响应头中。（后端可以快速定位到是哪次请求异常）
        response.addHeader(TraceIdUtil.TRACE_ID, TraceIdUtil.getTraceId());
        return new BaseOutDTO().fail(new BaseError().setCode(e.getCode()).setMsg(e.getMessage()));
    }
}
