package com.lwa.week.datasourceconfig;

import com.lwa.week.annotation.CurDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: lwa
 * @Date: 2021/11/7 17:20
 */
@Component
public class TwoDataSourceDemo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CurDataSource(name = DataSourceNames.FIRST)
    public void testFirst(){
        jdbcTemplate.execute("select 1");
    }

    @CurDataSource(name = DataSourceNames.SECOND)
    public void testSecond(){
        jdbcTemplate.execute("select 1");
    }

}
