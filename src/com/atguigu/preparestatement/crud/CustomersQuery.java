package com.atguigu.preparestatement.crud;

import com.atguigu.bean.Customer;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

/**
 *
 * 针对于customer表的一个查询操作
 *
 * @Author hliu
 * @Date 2023/2/3 21:22
 * @Version 1.0
 */
public class CustomersQuery {
    //通用的查询操作
    public void queryCustomers() {

    }

    @Test
    public void testQuery1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, 1);

            //执行，并返回结果集
            resultSet = preparedStatement.executeQuery();

            //处理结果集
            if (resultSet.next()) {//判断结果集的下一条是否有数据，并且指针下移
                //获取结果集各个字段的值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                //将数据封装为一个对象
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }


    }
}
