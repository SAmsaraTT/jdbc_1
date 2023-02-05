package com.atguigu.preparestatement.crud;

import com.atguigu.bean.Customer;
import com.atguigu.bean.Order;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对不同表的通用查询操作
 *
 * @Author hliu
 * @Date 2023/2/5 15:49
 * @Version 1.0
 */
public class PreparedStatementQueryTest {
    @Test
    public void testGetInstance() {
        String sql = "select id, name, email from customers where id = ?";
        Customer instance = getInstance(Customer.class, sql, 12);
        System.out.println(instance);

        String sql1 = "select order_id as orderId, order_name as orderName from `order` where order_id = ?";
        Class<Order> orderClass = Order.class;
        Order instance1 = getInstance(orderClass, sql1, 1);
        System.out.println(instance1);
    }

    @Test
    public void testGetList() {
        String sql = "select id, name, email from customers where id < ?";
        List<Customer> list = getList(Customer.class, sql, 12);
        list.forEach(System.out::println);
    }

    //返回一条记录
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
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
                T t = null;
                try {
                    t = clazz.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < columnCount; i++) {
                    Object object = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnLabel(i + 1);


                    //给customer对象的某个属性，赋值为value, 通过反射
                    Field declaredField = clazz.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, object);
                }
                return t;
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

    //返回多条记录
    public <T> List<T> getList(Class<T> clazz, String sql, Object... args) {
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
            List<T> ret = new ArrayList<>();
            while (resultSet.next()) {
                T t = null;
                try {
                    t = clazz.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < columnCount; i++) {
                    Object object = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnLabel(i + 1);


                    //给customer对象的某个属性，赋值为value, 通过反射
                    Field declaredField = clazz.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, object);
                }

                ret.add(t);
            }
            return ret;
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
    }
}
