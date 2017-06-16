package com.teresazl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��һ�����ݿ�Ĳ�������
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
		//ע������
		Class.forName("com.mysql.jdbc.Driver");
		//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		
		//��������
//		Connection conn = DriverManager.getConnection(
//				"jdbc:mysql://localhost:3306/jdbc", "root", "tzl123");
		String url = "jdbc:mysql://localhost:3306/jdbc";
		String user = "root";
		String password = "tzl123";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		//�������
		Statement st = conn.createStatement();
		
		//ִ�����
		ResultSet rs = st.executeQuery("select * from user");
		
		//��������
		while(rs.next())
		{
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t"
					+ rs.getDate(3) + "\t" + rs.getInt(4));
			
		}
		
		//�ͷ���Դ
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
