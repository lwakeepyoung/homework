package com.lwa.dynamicproxy.javaproxy;

import com.lwa.dynamicproxy.HelloService;
import com.lwa.dynamicproxy.HelloServiceImpl;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(helloService);
        HelloService hs = (HelloService)Proxy.newProxyInstance(
                HelloServiceImpl.class.getClassLoader(), new Class[]{HelloService.class}, myInvocationHandler);
        hs.hello();
    }
}
