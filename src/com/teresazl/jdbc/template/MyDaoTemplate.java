package com.teresazl.jdbc.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.teresazl.fin.JdbcUtils;
import com.teresazl.fin.dao.DaoException;

/**
 * @author Teresa
 *
 */
public class MyDaoTemplate
{
	public Object find(String sql, Object[] args, RawMapper rawMapper)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = JdbcUtils.getConnection();

			ps = conn.prepareStatement(sql);
			
			for(int i = 0; i < args.length; i++)
			{
				ps.setObject(i+1, args[i]);
			}
			
			rs = ps.executeQuery();
			
			Object obj = null;
			if(rs.next())
			{
				obj = rawMapper.rawMap(rs);
			}
			
			return obj;
		}
		catch (SQLException e)
		{
			throw new DaoException(e.getMessage(), e);
		}
		finally
		{
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
