package com.atguigu.connection;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author hliu
 * @Date 2023/1/30 10:59
 * @Version 1.0
 */
public class ConnectionTest {
    //方式1
    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8";
        //将用户名和密码封装在Properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "soul990719");

        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }

    //方式2：对方式1的迭代（不出现第三方的api使得程序具有更好的移植性）
    @Test
    public void testConnection2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //1.获取Driver实现类对象：使用反射
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)aClass.newInstance();

        //2.提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8";

        //3.提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "soul990719");

        //4.获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    //方式3：使用DriverManager替换Driver
    @Test
    public void testConnection3() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //1.获取Driver实现类对象：使用反射
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver)aClass.newInstance();

        //2.获取另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8";
        String user = "root";
        String password = "soul990719";

        //注册驱动
        DriverManager.registerDriver(driver);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
}
