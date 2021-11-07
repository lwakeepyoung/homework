package com.shardingdemo.shardingdemo;

import com.shardingdemo.shardingdemo.shardingsphere.ShardingSphereDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: lwa
 * @Date: 2021/11/7 21:06
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingSphereTest {

    @Autowired
    private ShardingSphereDemo shardingSphereDemo;

    @Test
    public void test(){
        shardingSphereDemo.testMaster();
        shardingSphereDemo.testSalve();
    }
}
