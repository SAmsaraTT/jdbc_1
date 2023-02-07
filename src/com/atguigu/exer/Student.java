package com.atguigu.exer;

/**
 * @Description Student对象 IDCard: ExamCard: StudentName: Location: Grade:
 * @Author hliu
 * @Date 2023/2/6 17:43
 * @Version 1.0
 */
public class Student {
    private int flowID;
    private int type;
    private String IDCard;
    private String examCard;
    private String name;
    private String location;
    private int grade;

    public Student() {
    }

    public Student(int flowID, int type, String IDCard, String examCard, String name, String location, int grade) {
        this.flowID = flowID;
        this.type = type;
        this.IDCard = IDCard;
        this.examCard = examCard;
        this.name = name;
        this.location = location;
        this.grade = grade;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getFlowID() {
        return flowID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "flowID=" + flowID +
                ", type=" + type +
                ", IDCard='" + IDCard + '\'' +
                ", examCard='" + examCard + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", grade=" + grade +
                '}';
    }
}
