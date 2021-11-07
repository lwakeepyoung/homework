package com.lwa.week.batchinsert;

import com.lwa.week.demo.JdbcUtil;
import com.lwa.week.demo.MyDataSource;
import com.lwa.week.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lwa
 * @Date: 2021/11/6 16:38
 */
@Component
public class JdbcDemo {

    @Autowired
    private MyDataSource myDataSource;

    public void testMyJdbc(List<Order> orderList) throws Exception {
        Connection connection = myDataSource.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO `shop`.`order` (user_id,create_time,update_time) values(?,?,?)";
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < orderList.size(); i++) {
                preparedStatement.setLong(1,orderList.get(i).getUserId());
                preparedStatement.setDate(2,orderList.get(i).getCreateTime());
                preparedStatement.setDate(3,orderList.get(i).getUpdateTime());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }finally {
            JdbcUtil.close(preparedStatement,null);
            myDataSource.releaseConnection(connection);
        }

    }


}
