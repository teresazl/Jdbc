package com.teresazl.jdbc;

import com.teresazl.fin.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Teresa
 */
public class ResultSetMetaDataTest {
    public static void main(String[] args) throws SQLException {
        //Map<String, Object> data = read("select id,name from user where id=3");
        List<Map<String, Object>> data = read("select id,name from user where id<10");
        System.out.println(data);

    }

    public static List<Map<String, Object>> read(String sql) throws SQLException {
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
                //System.out.println(rsmd.getColumnClassName(i) + "\t" + rsmd.getColumnName(i) + "\t" + rsmd.getColumnLabel(i));
                colNames[i - 1] = rsmd.getColumnLabel(i);
            }

            //这是返回一个map对象
            /*
			Map<String, Object> data = null;
			if(rs.next())
			{
				data = new HashMap<String, Object>();
				for(int i=0;i<colNames.length;i++)
				{
					data.put(colNames[i], rs.getObject(colNames[i]));
				}
			}
			*/
            //返回一个list对象

            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                Map<String, Object> data = new HashMap<String, Object>();
                for (int i = 0; i < colNames.length; i++) {
                    data.put(colNames[i], rs.getObject(colNames[i]));
                }
                datas.add(data);
            }

            return datas;
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }
}
