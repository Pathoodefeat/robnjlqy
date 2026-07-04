package com.sms.service;

import com.sms.dao.StudentDao;
import com.sms.entity.Student;
import com.sms.exception.BusinessException;

import java.util.List;

/**
 * 学生业务逻辑层：输入校验 + 业务规则
 */
public class StudentService {

    private final StudentDao studentDao = new StudentDao();

    public List<Student> list(String keyword) {
        return studentDao.findAll(keyword);
    }

    public Student get(String studentNo) {
        Student s = studentDao.findByNo(studentNo);
        if (s == null) {
            throw new BusinessException("学号 " + studentNo + " 的学生不存在");
        }
        return s;
    }

    public void add(Student s) {
        validate(s);
        if (studentDao.findByNo(s.getStudentNo()) != null) {
            throw new BusinessException("学号 " + s.getStudentNo() + " 已存在，不能重复添加");
        }
        studentDao.insert(s);
    }

    public void update(Student s) {
        validate(s);
        get(s.getStudentNo());
        studentDao.update(s);
    }

    public void delete(String studentNo) {
        get(studentNo);
        studentDao.delete(studentNo);
    }

    private void validate(Student s) {
        if (s.getStudentNo() == null || !s.getStudentNo().matches("\\w{4,20}")) {
            throw new BusinessException("学号必须为 4-20 位字母或数字");
        }
        if (s.getName() == null || s.getName().trim().isEmpty()) {
            throw new BusinessException("姓名不能为空");
        }
        if (!"男".equals(s.getGender()) && !"女".equals(s.getGender())) {
            throw new BusinessException("性别只能为男或女");
        }
        if (s.getAge() < 10 || s.getAge() > 60) {
            throw new BusinessException("年龄必须在 10-60 之间");
        }
        if (s.getMajor() == null || s.getMajor().trim().isEmpty()) {
            throw new BusinessException("专业不能为空");
        }
        if (s.getClassName() == null || s.getClassName().trim().isEmpty()) {
            throw new BusinessException("班级不能为空");
        }
        if (s.getPhone() != null && !s.getPhone().isEmpty() && !s.getPhone().matches("\\d{6,20}")) {
            throw new BusinessException("联系电话格式不正确");
        }
    }
}
