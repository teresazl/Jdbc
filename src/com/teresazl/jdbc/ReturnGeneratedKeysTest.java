package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.*;

/**
 * @author Teresa
 */
public class ReturnGeneratedKeysTest {
    public static void main(String[] args) throws SQLException {
        System.out.println(read());
    }

    public static int read() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "insert into user(name,birthday,age) values('name1','2014-01-28',30)";

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //设置返回主键

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
}
