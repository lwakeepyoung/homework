package com.lwa.dynamicproxy;

public class HelloServiceImpl implements HelloService{

    public HelloServiceImpl(){
        System.out.println("调用HelloService构造函数");
    }

    @Override
    public void hello(){
        System.out.println("hello");
    }
}
