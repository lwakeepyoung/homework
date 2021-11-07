package com.lwa.week.demo;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) {
        String userName = "root";
        String password = "111111";
        String url = "jdbc:mysql://localhost:3306/mysql";
        String driverClass = "com.mysql.cj.jdbc.Driver";
        String sql = "select * from user";
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet = null;
        try {
            //加载驱动注册到DriverManger
           // Class.forName(driverClass);
            connection = DriverManager.getConnection(url, userName, password);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt(2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
