package com.lwa.week;

import com.lwa.week.datasourceconfig.TwoDataSourceDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: lwa
 * @Date: 2021/11/7 17:19
 */
@SpringBootTest
public class TwoDataSourceTest {

    @Autowired
    private TwoDataSourceDemo twoDataSourceDemo;

    @Test
    public void test(){
        twoDataSourceDemo.testFirst();
        twoDataSourceDemo.testSecond();
    }

}
