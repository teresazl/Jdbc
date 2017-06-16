package com.teresazl.fin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库的4种基本操作
 * C-创建 R-读取 U-更新 D-删除
 *
 * @author Teresa
 */

public class CRUD {
    public static void main(String[] args) throws Exception {
        // create();
        // update();
        // delete();
        // read();
    }

    public static void read() throws SQLException {
        // 创建连接
        Connection conn = null;
        // 创建语句
        Statement st = null;
        // 执行语句
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("select id,name,birthday,age from user");

            // 处理数据
            while (rs.next()) {
                System.out
                        .println(rs.getInt("id") + "\t"
                                + rs.getString("name") + "\t"
                                + rs.getDate("birthday") + "\t"
                                + rs.getInt("age"));
            }
        } finally {
            // 释放资源
            JdbcUtils.free(rs, st, conn);
        }
    }

    public static void create() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            String sql = "insert into user(name,birthday,age) values('wangwu','2013-12-1','35')";
            int i = st.executeUpdate(sql);

            System.out.println("i = " + i);

        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }

    public static void update() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            String sql = "update user set age = age + 1";
            int i = st.executeUpdate(sql);

            System.out.println("i = " + i);

        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }

    public static void delete() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            String sql = "delete from user where id > 0";
            int i = st.executeUpdate(sql);

            System.out.println("i = " + i);

        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }
}
