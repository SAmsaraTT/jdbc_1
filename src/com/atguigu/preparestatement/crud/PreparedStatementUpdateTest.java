package com.atguigu.preparestatement.crud;

import com.atguigu.connection.ConnectionTest;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 使用PreparedStatement来替换Statement，实现数据表的增删改操作
 *
 * @Author hliu
 * @Date 2023/2/1 20:06
 * @Version 1.0
 */
public class PreparedStatementUpdateTest {
    //向customers表中添加一条数据
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
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

            connection = DriverManager.getConnection(url, user, password);
//        System.out.println(connection);

            //4.预编译sql语句，返回PreparedStatement的实例
            String sql = "insert into customers(name, email, birth)values(?,?,?)";//占位符
            preparedStatement = connection.prepareStatement(sql);

            //5.填充占位符
            preparedStatement.setString(1, "哪吒");
            preparedStatement.setString(2, "nezha@gmail.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = simpleDateFormat.parse("1000-01-01");
            preparedStatement.setDate(3, new java.sql.Date(parse.getTime()));

            //6.执行操作
            preparedStatement.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            //7.资源的关闭
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
}
