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

            conn.setAutoCommit(false); //������

            st = conn.createStatement();

            String sql = "update user set age=age-1 where id=3";
            st.executeUpdate(sql);

            sp = conn.setSavepoint(); //���洢��㣬�ع�������㣬֮ǰ�Ĳ��������ύ

            sql = "update user set age=age-1 where id=5";
            st.executeUpdate(sql);

            sql = "select age from user where id=4";
            rs = st.executeQuery(sql);
            int age = 0;
            // ��������
            if (rs.next()) {
                age = rs.getInt("age");
            }
            if (age < 50) {
                throw new RuntimeException("�����С");
            }
            sql = "update user set age=age+1 where id=2";
            st.executeUpdate(sql);

            conn.commit();
        } catch (RuntimeException e) {
            if (conn != null && sp != null) {
                conn.rollback(sp); //����ع�������㣬�����쳣ʱ
                conn.commit(); //�ύ����
            }
            throw e;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); //����ع��������쳣ʱ
            }
            throw e;
        } finally {
            // �ͷ���Դ
            JdbcUtils.free(rs, st, conn);
        }
    }
}
