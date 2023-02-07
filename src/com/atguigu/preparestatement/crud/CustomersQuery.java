package com.atguigu.preparestatement.crud;

import com.atguigu.bean.Customer;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
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
    @Test
    public void testQueryCustomer() {
        String sql = "select id, name, birth, email from customers where id = ?";
        Customer customer = queryCustomers(sql, 13);
        System.out.println(customer);

        String sql1 = "select name, email from customers where name = ?";
        Customer customer1 = queryCustomers(sql1, "周杰伦");
        System.out.println(customer1);
    }


    /**
    * @Description: 通用的查询操作
    * @Param: [sql, args]
    * @return: com.atguigu.bean.Customer
    * @Author: hliu
    * @Date: 2023/2/5
    */
    public Customer queryCustomers(String sql, Object ...args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                Customer customer = new Customer();
                for (int i = 0; i < columnCount; i++) {
                    Object object = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnLabel(i + 1);


                    //给customer对象的某个属性，赋值为value, 通过反射
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(customer, object);
                }
                return customer;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
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
