package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.*;
import java.util.Date;

/**
 * 测试日期读写的程序
 *
 * @author Teresa
 */

public class DateTest {
    public static void main(String[] args) throws SQLException {
//		create("xiaoxiao", new Date(), 20);
        System.out.println(readBirthday(2));
    }

    /**
     * 创建一个日期
     *
     * @param name
     * @param birthday
     * @param age
     * @throws SQLException
     */
    public static void create(String name, Date birthday, int age) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "insert into user(name,birthday,age) values(?,?,?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setDate(2, new java.sql.Date(birthday.getTime()));
            ps.setInt(3, age);

            int i = ps.executeUpdate();

            System.out.println("i = " + i);

        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }


    /**
     * 读取一个日期
     *
     * @param id
     * @return java.util.Date
     * @throws SQLException
     */
    public static Date readBirthday(int id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Date birthday = null;

        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("select birthday from user where id =" + id);

            while (rs.next()) {
                birthday = rs.getDate("birthday");
            }
        } finally {
            JdbcUtils.free(rs, st, conn);
        }

        return birthday;
    }

}
