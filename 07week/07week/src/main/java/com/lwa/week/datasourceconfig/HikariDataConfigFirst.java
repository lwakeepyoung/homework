package com.lwa.week.datasourceconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lwa
 * @Date: 2021/11/7 16:39
 */

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.first")
@Data
public class HikariDataConfigFirst {
    @Value("username")
    private String username;
    @Value("url")
    private String url;
    @Value("password")
    private String password;

}
