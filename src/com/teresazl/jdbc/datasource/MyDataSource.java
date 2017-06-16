package com.teresazl.jdbc.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 简单的数据源实现
 *
 * @author Teresa
 */
public class MyDataSource {
    private LinkedList<Connection> connectionsPool = new LinkedList<Connection>();
    private static String url = "jdbc:mysql://localhost:3306/jdbc";
    private static String user = "root";
    private static String password = "root";

    private static int initSize = 5;
    private static int maxSize = 10;
    private int currentSize = 0;

    public MyDataSource() {
        try {
            for (int i = 0; i < initSize; i++) {
                this.connectionsPool.addLast(this.createConnection());
                currentSize++;
            }
        } catch (SQLException e) {
            throw new ExceptionInInitializerError();
        }
    }

    public void free(Connection conn) {
        currentSize--;
        this.connectionsPool.addLast(conn);
    }

    public Connection getConnection() throws SQLException {
        synchronized (connectionsPool) {
            if (connectionsPool.size() > 0) {
                currentSize--;
                return this.connectionsPool.removeFirst();
            }

            if (currentSize < maxSize) {
                currentSize++;
                return this.createConnection();
            }
        }

        throw new SQLException("没有连接");
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
