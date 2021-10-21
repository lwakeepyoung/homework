package com.lwa.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InitUser {

    @Bean
    public User createUser(){
        return new User("张三","138888888888");
    }


}
