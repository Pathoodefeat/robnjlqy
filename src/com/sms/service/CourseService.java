package com.sms.service;

import com.sms.dao.CourseDao;
import com.sms.entity.Course;
import com.sms.exception.BusinessException;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程业务逻辑层
 */
public class CourseService {

    private final CourseDao courseDao = new CourseDao();

    public List<Course> list(String keyword) {
        return courseDao.findAll(keyword);
    }

    public Course get(String courseNo) {
        Course c = courseDao.findByNo(courseNo);
        if (c == null) {
            throw new BusinessException("课程编号 " + courseNo + " 不存在");
        }
        return c;
    }

    public void add(Course c) {
        validate(c);
        if (courseDao.findByNo(c.getCourseNo()) != null) {
            throw new BusinessException("课程编号 " + c.getCourseNo() + " 已存在");
        }
        courseDao.insert(c);
    }

    public void update(Course c) {
        validate(c);
        get(c.getCourseNo());
        courseDao.update(c);
    }

    public void delete(String courseNo) {
        get(courseNo);
        courseDao.delete(courseNo);
    }

    private void validate(Course c) {
        if (c.getCourseNo() == null || !c.getCourseNo().matches("\\w{2,20}")) {
            throw new BusinessException("课程编号必须为 2-20 位字母或数字");
        }
        if (c.getCourseName() == null || c.getCourseName().trim().isEmpty()) {
            throw new BusinessException("课程名称不能为空");
        }
        if (c.getCredit() == null || c.getCredit().compareTo(BigDecimal.ZERO) <= 0
                || c.getCredit().compareTo(new BigDecimal("20")) > 0) {
            throw new BusinessException("学分必须在 0-20 之间");
        }
    }
}
