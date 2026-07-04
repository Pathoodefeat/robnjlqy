package com.sms.service;

import com.sms.dao.CourseDao;
import com.sms.dao.ScoreDao;
import com.sms.dao.StudentDao;
import com.sms.entity.CourseStat;
import com.sms.entity.Score;
import com.sms.entity.StudentRank;
import com.sms.exception.BusinessException;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成绩业务逻辑层：录入/修改校验 + 统计分析
 */
public class ScoreService {

    private final ScoreDao scoreDao = new ScoreDao();
    private final StudentDao studentDao = new StudentDao();
    private final CourseDao courseDao = new CourseDao();

    public List<Score> query(String studentNo, String courseNo) {
        return scoreDao.find(studentNo, courseNo);
    }

    public Score get(int id) {
        Score s = scoreDao.findById(id);
        if (s == null) {
            throw new BusinessException("成绩记录不存在");
        }
        return s;
    }

    public void add(String studentNo, String courseNo, BigDecimal score) {
        checkScore(score);
        if (studentDao.findByNo(studentNo) == null) {
            throw new BusinessException("学号 " + studentNo + " 的学生不存在");
        }
        if (courseDao.findByNo(courseNo) == null) {
            throw new BusinessException("课程编号 " + courseNo + " 不存在");
        }
        if (scoreDao.exists(studentNo, courseNo)) {
            throw new BusinessException("该学生此课程成绩已存在，请使用修改功能");
        }
        Score s = new Score();
        s.setStudentNo(studentNo);
        s.setCourseNo(courseNo);
        s.setScore(score);
        scoreDao.insert(s);
    }

    public void update(int id, BigDecimal score) {
        checkScore(score);
        get(id);
        scoreDao.update(id, score);
    }

    public void delete(int id) {
        get(id);
        scoreDao.delete(id);
    }

    public List<CourseStat> statByCourse() {
        return scoreDao.statByCourse();
    }

    public List<StudentRank> rankByAvg() {
        return scoreDao.rankByAvg();
    }

    private void checkScore(BigDecimal score) {
        if (score == null || score.compareTo(BigDecimal.ZERO) < 0
                || score.compareTo(new BigDecimal("100")) > 0) {
            throw new BusinessException("成绩必须在 0-100 之间");
        }
    }
}
