package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.*;

/**
 * @author Teresa
 */
public class ParameterMetaDataTest {
    public static void main(String[] args) throws SQLException {
        read("select * from user where name=? and age=?", null);
    }

    public static void read(String sql, Object[] params) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //url = "jdbc:mysql://localhost:3306/jdbc?generateSimpleParameterMetadata=true";
            conn = JdbcUtils.getConnection();

            ps = conn.prepareStatement(sql);

            ParameterMetaData parameterMetaData = ps.getParameterMetaData();
//			int paramsCount = parameterMetaData.getParameterCount();

            for (int i = 1; i <= params.length; i++) {
                //System.out.println(parameterMetaData.getParameterClassName(i) + parameterMetaData.getParameterType(i) + parameterMetaData.getParameterTypeName(i));
                ps.setObject(i, params[i - 1]); //放置参数，填充占位符
            }

//			rs = ps.executeQuery();
//			while (rs.next())
//			{
//				System.out
//						.println(rs.getInt("id") + "\t"
//								+ rs.getString("name") + "\t"
//								+ rs.getDate("birthday") + "\t"
//								+ rs.getInt("age"));
//			}
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }
}