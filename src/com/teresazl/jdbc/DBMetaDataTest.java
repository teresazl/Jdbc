package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author Teresa
 */
public class DBMetaDataTest {
    public static void main(String[] args) throws SQLException {
        Connection conn = JdbcUtils.getConnection();

        DatabaseMetaData dbmd = conn.getMetaData();

        System.out.println("name : " + dbmd.getDatabaseProductName());
        System.out.println("支持事务：" + dbmd.supportsTransactions());

        conn.close();
    }
}
