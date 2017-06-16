package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Teresa
 */
public class ScrollTest {
    public static void main(String[] args) {

    }

    public static void read() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //设置可滚动

            rs = st.executeQuery("select id,name,birthday,age from user"); //select id,name,birthday,age from user limit 100 10 为mysql提供的分页方式

            // 处理数据
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDate("birthday") + "\t"
                        + rs.getInt("age"));
            }

            //往前翻
            if (rs.previous()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDate("birthday") + "\t"
                        + rs.getInt("age"));
            }

            //如果数据库没有提供分页功能，可以手动分页，不过值得注意的是这种方式效率很低
            rs.absolute(100);
            int i = 0;
            while (rs.next() && i < 10) {
                i++; //取出10个数据
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDate("birthday") + "\t"
                        + rs.getInt("age"));
            }
        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }
}
