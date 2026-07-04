package com.sms.entity;

/**
 * 学生实体类
 */
public class Student {

    private String studentNo;
    private String name;
    private String gender;
    private int age;
    private String major;
    private String className;
    private String phone;

    public Student() {
    }

    public Student(String studentNo, String name, String gender, int age,
                   String major, String className, String phone) {
        this.studentNo = studentNo;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.className = className;
        this.phone = phone;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
