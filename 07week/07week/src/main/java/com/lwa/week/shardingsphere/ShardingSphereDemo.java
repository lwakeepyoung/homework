package com.lwa.week.shardingsphere;

import com.lwa.week.annotation.CurDataSource;
import com.lwa.week.datasourceconfig.DataSourceNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: lwa
 * @Date: 2021/11/7 21:05
 */
@Component
public class ShardingSphereDemo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testMaster(){
        jdbcTemplate.execute("select 1");
    }


    public void testSalve(){
        jdbcTemplate.execute("insert into shop.order (user_id) values (1)");
    }
}
