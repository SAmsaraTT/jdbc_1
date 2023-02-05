package com.atguigu.exer;

import com.atguigu.preparestatement.crud.PreparedStatementQueryTest;
import com.atguigu.preparestatement.crud.PreparedStatementUpdateTest;
import org.junit.Test;

/**
 * @Author hliu
 * @Date 2023/2/5 16:27
 * @Version 1.0
 */
public class Ex1Test {
    @Test
    public void testInsert() {
        PreparedStatementUpdateTest preparedStatementUpdateTest = new PreparedStatementUpdateTest();
        String name = "hliu";
        String email = "skulllht@gmail.com";
        String birthday = "1999-07-19";

        String sql = "insert into customers(name, email, birth)values(?, ?, ?)";
        int update = preparedStatementUpdateTest.update(sql, name, email, birthday);
        if (update > 0) {
            System.out.println("successful!");
        } else {
            System.out.println("failure!");
        }
    }
}
