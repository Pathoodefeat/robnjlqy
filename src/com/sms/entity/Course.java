package com.sms.entity;

import java.math.BigDecimal;

/**
 * 课程实体类
 */
public class Course {

    private String courseNo;
    private String courseName;
    private BigDecimal credit;
    private String teacher;

    public Course() {
    }

    public Course(String courseNo, String courseName, BigDecimal credit, String teacher) {
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.credit = credit;
        this.teacher = teacher;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
