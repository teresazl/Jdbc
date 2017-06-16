package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.*;

/**
 * @author Teresa
 */
public class SavePointTest {
    public static void main(String[] args) throws SQLException {
        read();
    }

    public static void read() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Savepoint sp = null;

        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false); //打开事务

            st = conn.createStatement();

            String sql = "update user set age=age-1 where id=3";
            st.executeUpdate(sql);

            sp = conn.setSavepoint(); //保存储存点，回滚到这个点，之前的操作将被提交

            sql = "update user set age=age-1 where id=5";
            st.executeUpdate(sql);

            sql = "select age from user where id=4";
            rs = st.executeQuery(sql);
            int age = 0;
            // 处理数据
            if (rs.next()) {
                age = rs.getInt("age");
            }
            if (age < 50) {
                throw new RuntimeException("年龄过小");
            }
            sql = "update user set age=age+1 where id=2";
            st.executeUpdate(sql);

            conn.commit();
        } catch (RuntimeException e) {
            if (conn != null && sp != null) {
                conn.rollback(sp); //事务回滚到保存点，出现异常时
                conn.commit(); //提交事务
            }
            throw e;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); //事务回滚，出现异常时
            }
            throw e;
        } finally {
            // 释放资源
            JdbcUtils.free(rs, st, conn);
        }
    }
}
