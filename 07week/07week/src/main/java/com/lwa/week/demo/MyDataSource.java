package com.lwa.week.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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

    private Deque<Connection> connections;

    private int minConnections = 10;

    private int maxConnections = 50;

    @PostConstruct
    public void init(){
        try {
            connections = new ArrayDeque<>(minConnections);
            for (int i = 0; i < minConnections; i++) {
                Connection connection = DriverManager.getConnection(myJdbcConfig.getUrl(),
                        myJdbcConfig.getUserName(),
                        myJdbcConfig.getPassword());
                connections.add(connection);
            }

        } catch (SQLException e) {
            log.error("创建链接失败");
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws Exception {
        if(!connections.isEmpty()){
            return connections.poll();
        }else {
            if(connections.size()>=maxConnections){
                throw new Exception("没有链接可用");
            }
            Connection connection = DriverManager.getConnection(myJdbcConfig.getUrl(),
                    myJdbcConfig.getUserName(),
                    myJdbcConfig.getPassword());
            return connection;
        }
    }

    public synchronized void releaseConnection(Connection connection){
        if(connections.size()==maxConnections){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            connections.add(connection);
        }
    }

}
