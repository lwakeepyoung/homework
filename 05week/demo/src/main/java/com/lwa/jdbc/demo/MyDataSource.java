package com.lwa.jdbc.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @Author: lwa
 * @Date: 2021/10/21 21:28
 */
@Component
@Slf4j
@Data
public class MyDataSource {

    @Autowired
    private MyJdbcConfig myJdbcConfig;

    private Connection[] connections;

    @PostConstruct
    public void init(){
        try {
            int minConnections = 10;
            connections = new Connection[minConnections];
            for (int i = 0; i < minConnections; i++) {
                Connection connection = DriverManager.getConnection(myJdbcConfig.getUrl(),
                        myJdbcConfig.getUserName(),
                        myJdbcConfig.getPassword());
                connections[i] = connection;
            }

        } catch (SQLException e) {
            log.error("创建链接失败");
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws Exception {
        if(connections.length>0){
            Connection connection = connections[connections.length-1];
            connections[connections.length-1] = null;
            return connection;
        }else {
            throw new Exception("没有链接可用");
        }
    }

    public synchronized void releaseConnection(Connection connection){
        connections[connections.length-1] = connection;
    }

}
