package com.voilaf.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

    private HikariConfig config;
    private HikariDataSource ds;

    public DataSource() {
        config = new HikariConfig();
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/spring?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" );
        config.setUsername( "root" );
        config.setPassword( "123456" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    public void select() throws SQLException {
        String sql = "select * from user";
        try (Connection con = ds.getConnection();
             PreparedStatement pst = con.prepareStatement( sql );
             ResultSet rs = pst.executeQuery()) {
            while ( rs.next() ) {
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", name: " + name);
                System.out.print("\n");
            }
        }
    }

    public void close() {
        ds.close();
    }
}
