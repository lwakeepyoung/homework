package com.lwa.week.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lwa
 * @Date: 2021/10/21 21:14
 */
@Configuration
@ConfigurationProperties(prefix = "my.jdbc")
@Data
public class MyJdbcConfig {
    private String userName;
    private String password;
    private String url;
    private String driverClass;
}
