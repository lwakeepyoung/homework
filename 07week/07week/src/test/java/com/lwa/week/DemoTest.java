package com.lwa.week;

import com.lwa.week.batchinsert.HikariDemo;
import com.lwa.week.batchinsert.JdbcDemo;
import com.lwa.week.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: lwa
 * @Date: 2021/11/6 16:49
 */
@SpringBootTest
public class DemoTest {

    @Autowired
    private JdbcDemo jdbcDemo;

    @Autowired
    private HikariDemo hikariDemo;

    private Deque<List<Order>> orderList;

    private ExecutorService executorService;

    private long startTime=Long.MAX_VALUE;
    private long endTime = 0;
    private long resultTime=0;

    @PostConstruct
    public void init(){
        Date date =new Date();
        java.sql.Date now = new java.sql.Date(date.getTime());
        orderList = new ConcurrentLinkedDeque<>();
        executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{
                int size = 10_0000;
                List<Order> list = new ArrayList<>(size);
                for (int j = 0; j < size; j++) {
                    list.add(new Order(1L,now,now));
                }
                orderList.add(list);
            });
        }
    }


    @Test
    public void jdbcTest() throws Exception {
        while (true){
            if(orderList.size()==10){
                for (int i = 0; i < 5; i++) {
                    executorService.execute(()->{
                        try {
                            while (!orderList.isEmpty()){
                                long startTime = System.currentTimeMillis();
                                this.startTime = Math.min(this.startTime,startTime);
                                jdbcDemo.testMyJdbc(orderList.poll());
                                long endTime = System.currentTimeMillis();
                                this.endTime = Math.max(this.endTime,endTime);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            Thread.sleep(5000);
            if(resultTime != this.endTime-this.startTime){
                resultTime = this.endTime-this.startTime;
            }
            System.out.println("插入时间为："+resultTime/1000);
            break;
        }

    }

    @Test
    public void hikariTest() throws Exception {
        while (true){
            if(orderList.size()==10){
                for (int i = 0; i < 5; i++) {
                    executorService.execute(()->{
                        try {
                            while (!orderList.isEmpty()){
                                long startTime = System.currentTimeMillis();
                                this.startTime = Math.min(this.startTime,startTime);
                                hikariDemo.test(orderList.poll());
                                long endTime = System.currentTimeMillis();
                                this.endTime = Math.max(this.endTime,endTime);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            Thread.sleep(5000);
            if(resultTime != this.endTime-this.startTime){
                resultTime = this.endTime-this.startTime;
            }else {
                System.out.println("插入时间为："+Double.valueOf(resultTime)/1000);
                break;
            }
        }

    }



}
