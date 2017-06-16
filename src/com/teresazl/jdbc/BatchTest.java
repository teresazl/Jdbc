package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.*;

/**
 * @author Teresa
 */
public class BatchTest {
    public static void main(String[] args) throws SQLException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            //read(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("normal " + (end - start));

        start = System.currentTimeMillis();
        //read();
        end = System.currentTimeMillis();
        System.out.println("batch " + (end - start));
    }

    public static int read(int i) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "insert into user(name,birthday,age) values(?,?,?)";

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //设置返回主键
            ps.setString(1, "normal name" + i);
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setInt(3, 30);

            ps.execute();

            rs = ps.getGeneratedKeys(); //拿到主键，为什么为ResultSet，主键可能为复合主键

            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }

    public static void read() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "insert into user(name,birthday,age) values(?,?,?)";

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //设置返回主键
            for (int i = 0; i < 100; i++) {
                ps.setString(1, "batch name" + i);
                ps.setDate(2, new Date(System.currentTimeMillis()));
                ps.setInt(3, 30);

                ps.addBatch(); //设置添加到包
            }

            int[] ids = ps.executeBatch(); //打包发送

        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }
}
