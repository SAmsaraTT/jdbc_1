package com.atguigu.blob;

import com.atguigu.bean.Customer;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * @Description 测试使用PreparedStatement来操作Blob类型的数据
 * @Author hliu
 * @Date 2023/2/6 18:19
 * @Version 1.0
 */
public class BlobTest {
    /**
    * @Description: 向customers中插入Blob类型的字段
    * @Param: []
    * @return: void
    * @Author: hliu
    * @Date: 2023/2/6
    */
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into customers(name, email, birth, photo)values (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, "gg");
            preparedStatement.setObject(2, "skulllht@gmail.com");
            preparedStatement.setObject(3, "1999-07-19");
            InputStream inputStream = new FileInputStream("微信图片_20221102233353.jpg");
            preparedStatement.setBlob(4, inputStream);
            preparedStatement.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }

    /**
    * @Description: 查询数据表中的Blob类型的字段
    * @Param: []
    * @return: void
    * @Author: hliu
    * @Date: 2023/2/6
    */
    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        InputStream binaryStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth, photo from customers where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 21);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
    //            int id = resultSet.getInt(1);
    //            String name = resultSet.getString(2);
    //            String email = resultSet.getString(3);
    //            Date birth = resultSet.getDate(4);
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date birth = resultSet.getDate("birth");

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

                Blob photo = resultSet.getBlob("photo");
                binaryStream = photo.getBinaryStream();
                fileOutputStream = new FileOutputStream("download.jpg");

                byte[] buffer = new byte[1024];
                int len;
                while ((len = binaryStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (binaryStream != null) {
                    binaryStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
    }
}
