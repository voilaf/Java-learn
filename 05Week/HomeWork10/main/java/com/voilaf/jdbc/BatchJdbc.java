package com.voilaf.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BatchJdbc {

    private Connection conn;

    public BatchJdbc() throws Exception {
        // 注册 JDBC 驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 打开链接
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "123456");
    }

    public void batchInsert() throws SQLException {
        conn.setAutoCommit(false);
        String sql = "insert into user values (null, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for (int i=0; i<10; i++) {
            preparedStatement.setString(1, "xiaowang" + (new Random().nextInt(1000)));
            preparedStatement.execute();
        }
        preparedStatement.close();

        conn.commit();
        conn.setAutoCommit(true);
    }

    public void close() {
        try{
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
