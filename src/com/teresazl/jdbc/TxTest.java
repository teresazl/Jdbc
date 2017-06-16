package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Teresa
 *         ����ACID��
 *         atomicity ԭ����
 *         consistency һ����
 *         isolation ������
 *         durability ������
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

            conn.setAutoCommit(false); //������

            //����������뼶��һ��ʹ��Ĭ�ϣ���ȥ�޸�
            //conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            st = conn.createStatement();

            String sql = "update user set age=age-1 where id=3";
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
