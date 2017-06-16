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

            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //���ÿɹ���

            rs = st.executeQuery("select id,name,birthday,age from user"); //select id,name,birthday,age from user limit 100 10 Ϊmysql�ṩ�ķ�ҳ��ʽ

            // ��������
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDate("birthday") + "\t"
                        + rs.getInt("age"));
            }

            //��ǰ��
            if (rs.previous()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDate("birthday") + "\t"
                        + rs.getInt("age"));
            }

            //������ݿ�û���ṩ��ҳ���ܣ������ֶ���ҳ������ֵ��ע��������ַ�ʽЧ�ʺܵ�
            rs.absolute(100);
            int i = 0;
            while (rs.next() && i < 10) {
                i++; //ȡ��10������
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
