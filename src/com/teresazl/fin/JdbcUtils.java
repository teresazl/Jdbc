package com.teresazl.fin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.teresazl.jdbc.datasource.MyDataSource;

/**
 * JDBC的工具包
 * 包括注册驱动，得到连接，释放资源
 *
 * @author Teresa
 */

public class JdbcUtils {
    private static String url = "jdbc:mysql://localhost:3306/jdbc";
    private static String user = "root";
    private static String password = "root";
    private static MyDataSource dataSource = null;

    /**
     * 注册驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dataSource = new MyDataSource();
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 创建连接
     *
     * @return Connection
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        //conn = DriverManager.getConnection(url, user, password);
        conn = dataSource.getConnection();

        return conn;
    }

    /**
     * 释放资源
     *
     * @param ResultSet
     * @param Statement
     * @param Connection
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
//					if(conn != null)
//					{
//						conn.close();
//					}
                    dataSource.free(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
