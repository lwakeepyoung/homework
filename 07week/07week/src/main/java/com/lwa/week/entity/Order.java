package com.lwa.week.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;


/**
 * @Author: lwa
 * @Date: 2021/11/6 16:59
 */
@Data
@AllArgsConstructor
public class Order {
    private Long userId;
    private Date createTime;
    private Date updateTime;
}
