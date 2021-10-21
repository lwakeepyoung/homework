package com.lwa.dynamicproxy.cglibproxy;

import com.lwa.dynamicproxy.HelloService;
import com.lwa.dynamicproxy.HelloServiceImpl;
import org.springframework.cglib.proxy.Enhancer;

public class MyCglib {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(new MyMethodInterceptor());
        HelloService proxy = (HelloService)enhancer.create();
        proxy.hello();
    }

}
