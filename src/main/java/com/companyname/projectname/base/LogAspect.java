package com.companyname.projectname.base;

import com.alibaba.fastjson.JSONObject;
import com.companyname.projectname.util.IpUtils;
import com.companyname.projectname.util.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一日志打印
 *
 * @author renbaojie
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * execution 这里表示AOP会拦截web目录下到所有类到所有方法
     */
    @Pointcut("execution(public * com.companyname.projectname.web.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void theBefore(JoinPoint joinPoint) {
    }

    @After("webLog()")
    public void theAfter() {
    }

    /**
     * 在环绕方法里做日志打印
     */
    @Around("webLog()")
    public Object theAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Map<String, Object> currMap = new HashMap<>(2);
        THREAD_LOCAL.set(currMap);

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Object[] args = joinPoint.getArgs();

        // 获取请求参数
        StringBuilder params = new StringBuilder();
        if (HttpMethod.GET.toString().equals(method)) {
            if (!StringUtils.isEmpty(queryString)) {
                params = new StringBuilder(URLDecoder.decode(queryString, "UTF-8"));
            }
        } else {
            if (!ObjectUtils.isEmpty(args)) {
                for (Object arg : args) {
                    if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
                        continue;
                    }
                    params.append(JSONObject.toJSONString(arg));
                }
            }
        }

        currMap.put("startTime", startTime);
        currMap.put("url", requestURI);

        log.info("请求--->: {},\ttraceId: {},\tIP: {},\t参数: {}"
                , requestURI
                , TraceIdUtil.createTraceId()
                , IpUtils.getRemoteIP(request)
                , params.toString());

        // 执行实际方法，result为方法执行返回值
        Object result = joinPoint.proceed();

        // 把traceId设置到响应头
        if (!ObjectUtils.isEmpty(response)) {
            response.addHeader(TraceIdUtil.TRACE_ID, TraceIdUtil.getTraceId());
        }
        Map getMap = THREAD_LOCAL.get();

        log.info("响应<---: {},\ttraceId: {},\t耗时: {}ms,\t结果: {}"
                , getMap.get("url")
                , TraceIdUtil.getTraceId()
                , System.currentTimeMillis() - (long) getMap.get("startTime")
                , JSONObject.toJSONString(result));

        THREAD_LOCAL.remove();
        TraceIdUtil.removeTraceId();
        return result;
    }
}
