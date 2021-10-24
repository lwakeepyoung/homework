package com.lwa.AutoConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAutoConfig {

    public MyAutoConfig(){
        System.out.println("自动装配生效");
    }


    @Bean(name = "student100")
    public Student createStudent(){
        return new Student(1,"lwa",null,null);
    }

    @Bean
    public Klass createKlass(){
        return new Klass();
    }

    @Bean
    public School school(){
        return new School();
    }
}
