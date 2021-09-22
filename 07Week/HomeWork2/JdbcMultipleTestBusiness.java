package com.example.springboot.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class JdbcMultipleTestBusiness implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void writeData() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CountDownLatch countDownLatch = new CountDownLatch(10);
        String sql = "insert into commodity (name, create_time, update_time) values ";

        int start = (int)(System.currentTimeMillis() / 1000);
        System.out.println("开始时间：" + start);
        for (int index=0; index<10; index++) {
            String threadIndex = String.valueOf(index);
            executorService.submit(() -> {
                StringBuilder tempSql = new StringBuilder(60000);
                tempSql.append(sql);
                for (int i=0; i<100000; i++) {
                    tempSql.append("(\""+ threadIndex + ("-" + i) + "\", now(), now()),");
                    if (i % 3000 == 0 && i > 0) {
                        tempSql.deleteCharAt(tempSql.length()-1);
                        jdbcTemplate.update(tempSql.toString());
                        tempSql.setLength(0);
                        tempSql.append(sql);
                    }
                }
                if (!sql.equals(tempSql.toString())) {
                    tempSql.deleteCharAt(tempSql.length()-1);
                    jdbcTemplate.update(tempSql.toString());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        int end = (int)(System.currentTimeMillis() / 1000);
        System.out.println("持续时间：" + (end - start));
        System.out.println("结束时间：" + end);
    }

    @Override
    public void run(String... args) throws Exception {
        writeData();
    }
}
