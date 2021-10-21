package com.lwa.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
public class Client {

    @Autowired
    private User user;

    @PreDestroy
    public void call(){
        System.out.println("装配用户名称"+user.getName()+"，电话号码为："+user.mobile);
    }

}
