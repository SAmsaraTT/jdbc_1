package com.atguigu.util;

import com.atguigu.connection.ConnectionTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的工具类
 *
 * @Author hliu
 * @Date 2023/2/2 17:57
 * @Version 1.0
 */
public class JDBCUtils {

    //获取数据库的连接
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        //1.读取配置文件的基本信息

        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2.加载驱动

        Class.forName(driverClass);

        //3.获取连接

        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

    //关闭资源的操作
    public static void closeResource(Connection connection, Statement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
