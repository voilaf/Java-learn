package com.voilaf.jdbc;

import java.sql.*;
import java.util.Random;

public class OriginJdbc {

    private Connection conn;

    public OriginJdbc() throws Exception {
        // 注册 JDBC 驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 打开链接
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "123456");
    }

    public void insert() throws SQLException {
        String name = "xiaowang" + (new Random().nextInt(1000));
        String sql = "insert into user values (null, '" + name + "')";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public void delete() throws SQLException {
        String sql = "delete from user where name like 'xiaowang%' and id != 1";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public void update() throws SQLException {
        String name = "xiaowang" + (new Random().nextInt(2000));
        String sql = "update user set name = '" + name + "' where id = 1";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public void select() {
        Statement stmt = null;
        try{
            // 执行查询
            stmt = conn.createStatement();
            String sql = "SELECT id, name FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", name: " + name);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2){ }
        }
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
