package com.teresazl.fin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ���ݿ��4�ֻ�������
 * C-���� R-��ȡ U-���� D-ɾ��
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
        // ��������
        Connection conn = null;
        // �������
        Statement st = null;
        // ִ�����
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("select id,name,birthday,age from user");

            // ��������
            while (rs.next()) {
                System.out
                        .println(rs.getInt("id") + "\t"
                                + rs.getString("name") + "\t"
                                + rs.getDate("birthday") + "\t"
                                + rs.getInt("age"));
            }
        } finally {
            // �ͷ���Դ
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
