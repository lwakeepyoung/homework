package com.lwa.AutoConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.ApplicationContext;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    private int id;
    private String name;

    private String beanName;

    private ApplicationContext applicationContext;

    public void init(){
        System.out.println("hello...........");
    }

    public void print() {
        System.out.println(this.beanName);
//        System.out.println("   context.getBeanDefinitionNames() ===>> "
//                + String.join(",", applicationContext.getBeanDefinitionNames()));

    }
}
