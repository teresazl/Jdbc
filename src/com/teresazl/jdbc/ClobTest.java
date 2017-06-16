package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.io.*;
import java.sql.*;

/**
 * @author Teresa
 */
public class ClobTest {
    public static void main(String[] args) throws SQLException, IOException {
//		create();
        read();
    }

    /**
     * 创建一个大文本到数据库
     *
     * @throws SQLException
     * @throws IOException
     */
    public static void create() throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "insert into clob_test(ClobText) values(?)";
            ps = conn.prepareStatement(sql);

            File file = new File("src/com/teresa/fin/JdbcUtil.java");
            Reader reader = new BufferedReader(new FileReader(file));

            ps.setCharacterStream(1, reader, file.length());

            int i = ps.executeUpdate();

            System.out.println("i = " + i);

            reader.close();
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }

    /**
     * @throws SQLException
     * @throws IOException
     */
    public static void read() throws SQLException, IOException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("select ClobText from clob_test");

            while (rs.next()) {
                Reader reader = rs.getCharacterStream(1);
                File file = new File("JdbcUtil_bak.java");
                Writer writer = new BufferedWriter(new FileWriter(file));

                //String string = rs.getString(1);

                char[] cbuff = new char[1024];
                for (int i = 0; (i = reader.read(cbuff)) > 0; ) {
                    writer.write(cbuff, 0, i);
                }

                writer.close();
                reader.close();
            }
        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }
}
