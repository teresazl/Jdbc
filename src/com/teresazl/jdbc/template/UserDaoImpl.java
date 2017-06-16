package com.teresazl.jdbc.template;

import com.teresazl.fin.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 策略模式
 *
 * @author Teresa
 */
public class UserDaoImpl {
    MyDaoTemplate daoTemplate = new MyDaoTemplate();

    public User findUser(int id) {
        String sql = "select id,name,birthday,age from user where id=?";
        Object[] args = new Object[]{id};

        Object user = daoTemplate.find(sql, args, new RawMapper() {

            @Override
            public Object rawMap(ResultSet rs) throws SQLException {
                User user;
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setBirthday(rs.getDate("birthday"));
                user.setAge(rs.getInt("age"));
                return user;
            }
        });

        return (User) user;
    }
}
