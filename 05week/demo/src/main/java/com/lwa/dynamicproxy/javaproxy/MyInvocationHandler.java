package com.lwa.dynamicproxy.javaproxy;

import com.lwa.dynamicproxy.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private HelloService helloService;

    public MyInvocationHandler(HelloService helloService){
        this.helloService = helloService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("插入前置通知");
        Long start = System.currentTimeMillis();
        Object object = method.invoke(helloService, args);
        Long end = System.currentTimeMillis();
        System.out.println("插入后置通知");
        System.out.println("方法运行时间"+(end-start));
        return object;
    }


}
