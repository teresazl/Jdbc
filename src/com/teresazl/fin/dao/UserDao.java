package com.teresazl.fin.dao;

import com.teresazl.fin.domain.User;

/**
 * @author Teresa
 */
public interface UserDao {
    public void addUser(User user);

    public User getUser(int userId);

    public User findUser(String loginName, String password);

    public void deleteUser(User user);

    public void updateUser(User user);
}
