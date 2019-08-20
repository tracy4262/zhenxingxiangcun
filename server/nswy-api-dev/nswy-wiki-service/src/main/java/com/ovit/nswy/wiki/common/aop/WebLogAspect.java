package com.ovit.nswy.wiki.common.aop;

import com.alibaba.fastjson.JSON;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.common.annotation.OperLog;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/** 切面记录相关日志
 *Created by ${huipei.x} on 2017-10-26.
 */
@Component
@Aspect
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    @Autowired
    private SpeciesWikiService speciesWikiService;

    @Pointcut("execution(public * com.ovit.nswy.*.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        logger.info("WebLogAspect.doBefore()");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("ParameterMap : " + JSON.toJSONString(request.getParameterMap()));
        startTime.set(System.currentTimeMillis());
        String method = request.getMethod();
        String params = "";
        Map<?, ?> paramsMap=null;
        if ("POST".equals(method)) {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method targetMethod = methodSignature.getMethod();
            Object[] paramsArray = joinPoint.getArgs();

        } else {
            paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            params = paramsMap.toString();
        }

        logger.info(" params=" + params);
        Integer count = 0;
        if (getControllerMthodvalue(joinPoint).equals("saveLikedCount")) {
            count++;
            Integer result= speciesWikiService.getLikedCount(Maps.getString((Map<Object, Object>) paramsMap, "indexId"));
            if(Integers.isNotEmpty(result)){
                speciesWikiService.updateLikedCount(Maps.getString((Map<Object, Object>) paramsMap,"indexId"),result+count);
            }else{
                speciesWikiService.updateLikedCount(Maps.getString((Map<Object, Object>) paramsMap, "indexId"),count);
            }

        }


}


    public  static String getControllerMthodvalue(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String value ="";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (method.getAnnotation(OperLog. class)!=null){
                    if (clazzs.length == clazzs.length) {
                        value = method.getAnnotation(OperLog. class).value();
                        break;
                    }
                }

            }
        }
        return value;
    }




    @AfterReturning("webLog()")
    public void  doAfterReturning(JoinPoint joinPoint){
        // 处理完请求，返回内容
        logger.info("WebLogAspect.doAfterReturning()");
        logger.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()));
    }
}
