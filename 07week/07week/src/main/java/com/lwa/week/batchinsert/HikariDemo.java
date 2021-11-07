package com.lwa.week.batchinsert;

import com.lwa.week.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: lwa
 * @Date: 2021/11/6 20:01
 */
@Component
public class HikariDemo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void test(List<Order> orderList){
        String sql = "INSERT INTO `shop`.`order` (user_id,create_time,update_time) values(?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1,orderList.get(i).getUserId());
                ps.setDate(2,orderList.get(i).getCreateTime());
                ps.setDate(3,orderList.get(i).getUpdateTime());
            }
            @Override
            public int getBatchSize() {
                return orderList.size();
            }
        });
    }
}
