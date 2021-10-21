package com.lwa.jdbc;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) {
        String userName = "";
        String password = "";
        String url = "";
        String driverClass = "com.mysql.cj.jdbc.Driver";
        String sql = "";
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet = null;
        try {
            //加载驱动注册到DriverManger
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, userName, password);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt(2);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
