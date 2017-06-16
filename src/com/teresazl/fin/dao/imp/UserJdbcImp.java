package com.teresazl.fin.dao.imp;

import com.teresazl.fin.JdbcUtils;
import com.teresazl.fin.dao.DaoException;
import com.teresazl.fin.dao.UserDao;
import com.teresazl.fin.domain.User;

import java.sql.*;

/**
 * @author Teresa
 */
public class UserJdbcImp implements UserDao {
    @Override
    public void addUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "insert into user(name,birthday,age) values(?,?,?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getName());
            ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
            ps.setLong(3, user.getAge());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }

    @Override
    public User getUser(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "select id,name,birthday,age from user where id=?";

            ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            rs = ps.executeQuery();

            // 处理数据
            while (rs.next()) {
                user = mappingUser(rs);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            // 释放资源
            JdbcUtils.free(rs, ps, conn);
        }
        return user;
    }

    @Override
    public User findUser(String loginName, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "select id,name,birthday,age from user where name=?";

            ps = conn.prepareStatement(sql);

            ps.setString(1, loginName);

            rs = ps.executeQuery();

            // 处理数据
            while (rs.next()) {
                user = mappingUser(rs);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            // 释放资源
            JdbcUtils.free(rs, ps, conn);
        }
        return user;
    }

    private User mappingUser(ResultSet rs) throws SQLException {
        User user;
        user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setBirthday(rs.getDate("birthday"));
        user.setAge(rs.getInt("age"));
        return user;
    }

    @Override
    public void deleteUser(User user) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            String sql = "delete from user where id =" + user.getId();
            st.executeUpdate(sql);

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }

    @Override
    public void updateUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();

            String sql = "update user set name=?,birthday=?,age=? where id=?";

            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
            ps.setLong(3, user.getAge());
            ps.setLong(4, user.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }

}
