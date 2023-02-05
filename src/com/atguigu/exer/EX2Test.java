package com.atguigu.exer;

import com.atguigu.preparestatement.crud.PreparedStatementUpdateTest;
import org.junit.Test;

/**
 * @Description 课后练习2
 * @Author hliu
 * @Date 2023/2/5 16:53
 * @Version 1.0
 */
public class EX2Test {

    /**
    * @Description: 问题1:向examstudent表中添加一条记录 Type: IDCard: ExamCard: StudentName: Location: Grade
    * @Param: []
    * @return: void
    * @Author: hliu
    * @Date: 2023/2/5
    */
    @Test
    public void testInsert() {
        PreparedStatementUpdateTest preparedStatementUpdateTest = new PreparedStatementUpdateTest();
        int type = 6;
        String IDCard = "123124426";
        String examCard = "12312314";
        String studentName = "hliu";
        String location = "ATL";
        int grade = 123123;

        String sql = "insert into examstudent(type, IDCard, examCard, studentName, location, grade) values(?,?,?,?,?,?)";
        int update = preparedStatementUpdateTest.update(sql, type, IDCard, examCard, studentName, location, grade);
        if (update > 0) {
            System.out.println("successfull!");
        } else {
            System.out.println("failure!");
        }
    }
}
