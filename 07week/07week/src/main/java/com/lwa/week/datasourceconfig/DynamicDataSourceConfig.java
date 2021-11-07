package com.lwa.week.datasourceconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lwa
 * @Date: 2021/11/7 16:33
 */
@Configuration
public class DynamicDataSourceConfig {

    @Autowired
    private HikariDataConfigFirst first;

    @Autowired
    private HikariDataConfigSecond second;

    @Bean(name = "firstDataSource")
    public DataSource firstDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(first.getUsername());
        hikariDataSource.setPassword(first.getPassword());
        hikariDataSource.setJdbcUrl(first.getUrl());
        return hikariDataSource;
    }

    @Bean(name = "secondDataSource")
    public DataSource secondDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(second.getUsername());
        hikariDataSource.setPassword(second.getPassword());
        hikariDataSource.setJdbcUrl(second.getUrl());
        return hikariDataSource;
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("firstDataSource") DataSource first,
                                        @Qualifier("secondDataSource")DataSource second){
        Map<Object, Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put(DataSourceNames.FIRST,first);
        targetDataSources.put(DataSourceNames.SECOND,second);
        return new DynamicDataSource(first,targetDataSources);
    }
}
