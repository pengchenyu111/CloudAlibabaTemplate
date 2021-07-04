package com.pcy.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.pcy.model.WebLog;
import com.pcy.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录切面
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class WebLogAspect {

    /**
     * 1 定义切入点
     * controller 包里面所有类，类里面的所有方法 都有该切面
     */
    @Pointcut("execution(* com.pcy.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 2 记录日志的环绕通知
     */
    @Around("webLog()")
    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis();
        // 执行方法的真实调用
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();
        // 请求该接口花费的时间
        webLog.setSpendTime((int) (end - start));
        // 获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(url);
        webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath()));
        // 获取ip 地址
        webLog.setIp(IpUtil.getIpAddr(request));
        // 获取方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获取类的名称
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        Method method = signature.getMethod();
        webLog.setMethod(targetClassName + "." + method.getName());
        webLog.setParameter(getMethodParameter(method, proceedingJoinPoint.getArgs()));
        webLog.setResult(result);
        log.info(JSON.toJSONString(webLog, true));
        return result;
    }

    /**
     * 获取方法的执行参数
     *
     * @param method
     * @param args
     * @return {"key_参数的名称":"value_参数的值"}
     */
    private Object getMethodParameter(Method method, Object[] args) {
        Map<String, Object> methodParametersWithValues = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        // 方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            if ("password".equals(parameterNames[i]) || "file".equals(parameterNames[i])) {
                methodParametersWithValues.put(parameterNames[i], "受限的支持类型");
            } else {
                methodParametersWithValues.put(parameterNames[i], args[i]);
            }

        }
        return methodParametersWithValues;
    }
}
