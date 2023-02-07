package com.atguigu.preparestatement.crud;

import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description 使用PreparedStatement实现批量插入数据的操作
 * 题目：向goods表中插入20000条数据
 * CREATE TABLE goods(
 * id INT PRIMARY KEY AUTO_INCREMENT,
 * NAME VARCHAR(25)
 * );
 * 方式1：
 * Connection conn = JDBCUtils.getConnection();
 * Statement st = conn.createStatement();
 * for(int i = 1;i <= 20000;i++){
 * String sql = "insert into goods(name)values('name_" + i + "')";
 * st.execute(sql);
 * }
 * @Author hliu
 * @Date 2023/2/7 17:42
 * @Version 1.0
 */


public class InsertTest {

    /**
     * @Description: 批量插入方式2：使用PreparedStatement
     * @Param: []
     * @return: void
     * @Author: hliu
     * @Date: 2023/2/7
     */
    @Test
    public void testInsert1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values (?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                preparedStatement.setObject(1, "name_" + i);
                preparedStatement.execute();
            }
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
     * @Description: 批量插入方式3：
     * 1.addBatch(), executeBatch(), clearBatch()
     * @Param: []
     * @return: void
     * @Author: hliu
     * @Date: 2023/2/7
     */
    @Test
    public void testInsert2() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values (?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 1000000; i++) {
                preparedStatement.setObject(1, "name_" + i);
                preparedStatement.addBatch();

                if (i % 500 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
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
     * @Description: 批量插入方式4
     * @Param: []
     * @return: void
     * @Author: hliu
     * @Date: 2023/2/7
     */
    @Test
    public void testInsert3() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            //设置不允许提交自动提交数据
            connection.setAutoCommit(false);

            String sql = "insert into goods(name)values (?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 1000000; i++) {
                preparedStatement.setObject(1, "name_" + i);
                preparedStatement.addBatch();

                if (i % 500 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }

            //提交数据
            connection.commit();
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
}
