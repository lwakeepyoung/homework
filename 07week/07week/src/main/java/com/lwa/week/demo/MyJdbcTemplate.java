package com.lwa.week.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: lwa
 * @Date: 2021/10/21 21:35
 */
@Component
public class MyJdbcTemplate {

    @Autowired
    private MyDataSource myDataSource;

    public void add(String sql) throws Exception {
        Connection connection = myDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }finally {
            JdbcUtil.close(preparedStatement,null);
            myDataSource.releaseConnection(connection);
        }

    }

    public int update(String sql) throws Exception {
        Connection connection = myDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            int count = preparedStatement.executeUpdate(sql);
            return count;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }finally {
            JdbcUtil.close(preparedStatement,null);
            myDataSource.releaseConnection(connection);
        }
        return 0;
    }

    public int delete(String sql) throws Exception {
        Connection connection = myDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute(sql);
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }finally {
            JdbcUtil.close(preparedStatement,null);
            myDataSource.releaseConnection(connection);
        }
        return 0;
    }

    public ResultSet select(String sql) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = myDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            myDataSource.releaseConnection(connection);
        }
        return null;
    }

}
