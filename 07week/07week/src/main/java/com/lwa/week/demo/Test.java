package com.lwa.week.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

/**
 * @Author: lwa
 * @Date: 2021/10/21 21:25
 */
@Component
public class Test {
    @Autowired
    public MyJdbcTemplate myJdbcTemplate;

    public void test() throws Exception {
        String addSql = "insert into user (name,age) values ('zs',30)";
        myJdbcTemplate.add(addSql);

        String selectSql = "select * from user";
        ResultSet resultSet = myJdbcTemplate.select(selectSql);
        while (resultSet.next()){
            String name = resultSet.getString("name");
            Integer age = resultSet.getInt(3);
            System.out.println(name);
            System.out.println(age);
        }

        String updateSql = "update user set user.name1 = 'ls' where id = 1";
        myJdbcTemplate.update(updateSql);

        String deleteSql = "delete from user where id = 2";
        myJdbcTemplate.delete(deleteSql);

    }
}
