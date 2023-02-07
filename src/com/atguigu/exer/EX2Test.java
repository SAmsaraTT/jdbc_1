package com.atguigu.exer;

import com.atguigu.preparestatement.crud.PreparedStatementQueryTest;
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

    /**
    * @Description: 问题2：根据身份证号或者准考证号查询学生的成绩信息
    * @Param: []
    * @return: void
    * @Author: hliu
    * @Date: 2023/2/6
    */
    @Test
    public void testQuery() {
        PreparedStatementQueryTest preparedStatementQueryTest = new PreparedStatementQueryTest();
        String examCard = "200523164754003";
        String sql = "select FlowID flowID, Type type, IDCard, ExamCard examCard, StudentName name, Location location, Grade grade from examstudent where examCard = ?";
        Student instance = preparedStatementQueryTest.getInstance(Student.class, sql, examCard);
        System.out.println(instance);

    }

    /**
    * @Description: 问题3：删除指定的学生信息
    * @Param: []
    * @return: void
    * @Author: hliu
    * @Date: 2023/2/6
    */
    @Test
    public void testDelete() {
        PreparedStatementQueryTest preparedStatementQueryTest = new PreparedStatementQueryTest();
        PreparedStatementUpdateTest preparedStatementUpdateTest = new PreparedStatementUpdateTest();
        String examCard = "200523164754000";
        String sql = "select FlowID flowID, Type type, IDCard, ExamCard examCard, StudentName name, Location location, Grade grade from examstudent where examCard = ?";
        Student instance = preparedStatementQueryTest.getInstance(Student.class, sql, examCard);
        if (instance == null) {
           throw new RuntimeException("cannot find the student!");
        }

        String sql1 = "delete from examstudent where examCard = ?";
        int update = preparedStatementUpdateTest.update(sql1, examCard);
        if (update > 0) {
            System.out.println("successful!");
        } else {
            System.out.println("failure!");
        }

    }

    /**
     * 删除方法的优化！
     */

    @Test
    public void testDelete1() {
        PreparedStatementUpdateTest preparedStatementUpdateTest = new PreparedStatementUpdateTest();
        String examCard = "12312314";
        String sql1 = "delete from examstudent where examCard = ?";
        int update = preparedStatementUpdateTest.update(sql1, examCard);
        if (update > 0) {
            System.out.println("successful!");
        } else {
            System.out.println("failure!");
        }
    }
}
