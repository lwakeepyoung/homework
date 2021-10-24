package com.lwa.hikari;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lwa
 * @Date: 2021/10/24 10:26
 */
@Service
public class HikariTest {

    @Autowired
    private HikariDataSource hikariDataSource;

}
