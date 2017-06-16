package com.teresazl.fin.dao;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Teresa
 */
public class DaoFactory {
    private static UserDao userDao = null;
    private static DaoFactory instance = new DaoFactory();

    private DaoFactory() {
        try {
            Properties prop = new Properties();
            InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream("daoconfig.properties");
            prop.load(input);
            String className = prop.getProperty("UserDaoClass");
            Class clazz = Class.forName(className);
            userDao = (UserDao) clazz.newInstance();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
