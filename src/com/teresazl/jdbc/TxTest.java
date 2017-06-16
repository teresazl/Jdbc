package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Teresa
 *         事务（ACID）
 *         atomicity 原子性
 *         consistency 一致性
 *         isolation 隔离性
 *         durability 持续性
 */
public class TxTest {
    public static void main(String[] args) throws SQLException {
        read();
    }

    public static void read() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false); //打开事务

            //设置事务隔离级别，一般使用默认，不去修改
            //conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            st = conn.createStatement();

            String sql = "update user set age=age-1 where id=3";
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
