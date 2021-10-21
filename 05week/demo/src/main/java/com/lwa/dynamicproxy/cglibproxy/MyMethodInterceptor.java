package com.lwa.dynamicproxy.cglibproxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("插入前置通知");
        Long start = System.currentTimeMillis();
        Object object = methodProxy.invokeSuper(sub,objects);
        Long end = System.currentTimeMillis();
        System.out.println("插入后置通知");
        System.out.println("方法运行时间"+(end-start));
        return object;
    }
}
