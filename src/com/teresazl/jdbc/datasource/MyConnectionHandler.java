package com.teresazl.jdbc.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * @author Teresa
 */
public class MyConnectionHandler implements InvocationHandler {
    Connection warpeConn;
    Connection realConn;
    MyDataSource dataSource;

    public MyConnectionHandler(MyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    Connection bind(Connection realConnection) {
        realConn = realConnection;
        warpeConn = (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Connection.class}, this);

        return warpeConn;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if ("close".equals(method.getName())) {

        }
        return method.invoke(realConn, args);
    }
}
