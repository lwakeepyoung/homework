package com.lwa.week.datasourceconfig;

import com.lwa.week.annotation.CurDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: lwa
 * @Date: 2021/11/7 17:06
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    @Pointcut("@annotation(com.lwa.week.annotation.CurDataSource)")
    public void dataSourcePointCut(){

    }
    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        CurDataSource annotation = method.getAnnotation(CurDataSource.class);
        if(annotation==null){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            log.info("set datasource is first");
        }else {
            DynamicDataSource.setDataSource(annotation.name());
            log.info("set datasource is "+annotation.name());
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            DynamicDataSource.clearDataSource();
        }
        return null;
    }
}
