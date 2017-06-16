package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;
import com.teresazl.fin.domain.User;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;

/**
 * @author Teresa
 */
public class ORMTest {
    public static void main(String[] args) throws Exception {
        User user = read("select id as Id,name as Name,birthday as Birthday,age as Age from user where id=3");
        // System.out.println(user);
        //User user = read("select id,name,birthday,age from user where id=3");
        System.out.println(user);
    }

    // 利用PropertyDescriptor
    //bean的属性描述符
    public static User read(String sql) throws SQLException, Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = ps.getMetaData();
            int col = rsmd.getColumnCount();
            String[] colNames = new String[col];
            for (int i = 1; i <= col; i++) {
                colNames[i - 1] = rsmd.getColumnLabel(i);
            }

            User user = null;
            PropertyDescriptor propertyDescriptor = null;
            if (rs.next()) {
                user = new User();
                for (int i = 0; i < colNames.length; i++) {
                    propertyDescriptor = new PropertyDescriptor(colNames[i],
                            user.getClass());

                    Method methodSet = propertyDescriptor.getWriteMethod();
                    System.out.println(colNames[i]);
                    methodSet.invoke(user, rs.getObject(colNames[i]));
                }
            }

            return user;
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }

    // 传统做法
    //sql语句要使用别名
//	public static User read(String sql) throws SQLException, Exception
//	{
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try
//		{
//			conn = JdbcUtil.getConnection();
//
//			ps = conn.prepareStatement(sql);
//
//			rs = ps.executeQuery();
//
//			ResultSetMetaData rsmd = ps.getMetaData();
//			int col = rsmd.getColumnCount();
//			String[] colNames = new String[col];
//			for (int i = 1; i <= col; i++)
//			{
//				colNames[i - 1] = rsmd.getColumnLabel(i);
//			}
//
//			User user = null;
//			String methodName = null;
//			String colName = null;
//			Method[] methods = User.class.getMethods();
//			if (rs.next())
//			{
//				user = new User();
//				for (int i = 0; i < colNames.length; i++)
//				{
//					colName = colNames[i];
//					methodName = "set" + colNames[i];
//
//					for (Method method : methods)
//					{
//						if (methodName.equals(method.getName()))
//						{
//							method.invoke(user, rs.getObject(colName));
//							break;
//						}
//					}
//				}
//			}
//
//			return user;
//		}
//		finally
//		{
//			JdbcUtil.free(rs, ps, conn);
//		}
//	}

}
