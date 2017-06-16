package com.teresazl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 第一个数据库的操作程序
 * @author Teresa
 *
 */

public class Base
{
	public static void main(String[] args) throws Exception
	{
		
	}
	
	public static void test() throws Exception
	{
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
		//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		
		//创建连接
//		Connection conn = DriverManager.getConnection(
//				"jdbc:mysql://localhost:3306/jdbc", "root", "tzl123");
		String url = "jdbc:mysql://localhost:3306/jdbc";
		String user = "root";
		String password = "tzl123";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		//创建语句
		Statement st = conn.createStatement();
		
		//执行语句
		ResultSet rs = st.executeQuery("select * from user");
		
		//处理数据
		while(rs.next())
		{
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t"
					+ rs.getDate(3) + "\t" + rs.getInt(4));
			
		}
		
		//释放资源
//		rs.close();
//		st.close();
//		conn.close();
		try
		{
			if(rs != null)
			{
				rs.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(st != null)
				{
					st.close();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(conn != null)
					{
						conn.close();
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
