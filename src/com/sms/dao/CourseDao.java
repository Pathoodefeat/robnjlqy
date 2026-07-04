package com.sms.dao;

import com.sms.entity.Course;
import com.sms.exception.DataAccessException;
import com.sms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程数据访问对象（JDBC 实现增删改查）
 */
public class CourseDao {

    private Course mapRow(ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("course_no"),
                rs.getString("course_name"),
                rs.getBigDecimal("credit"),
                rs.getString("teacher"));
    }

    public List<Course> findAll(String keyword) {
        StringBuilder sql = new StringBuilder(
                "SELECT course_no, course_name, credit, teacher FROM t_course");
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        if (hasKeyword) {
            sql.append(" WHERE course_no LIKE ? OR course_name LIKE ? OR teacher LIKE ?");
        }
        sql.append(" ORDER BY course_no");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            if (hasKeyword) {
                String like = "%" + keyword.trim() + "%";
                for (int i = 1; i <= 3; i++) {
                    ps.setString(i, like);
                }
            }
            rs = ps.executeQuery();
            List<Course> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("查询课程列表失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    public Course findByNo(String courseNo) {
        String sql = "SELECT course_no, course_name, credit, teacher FROM t_course WHERE course_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseNo);
            rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        } catch (SQLException e) {
            throw new DataAccessException("查询课程失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    public void insert(Course c) {
        String sql = "INSERT INTO t_course (course_no, course_name, credit, teacher) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCourseNo());
            ps.setString(2, c.getCourseName());
            ps.setBigDecimal(3, c.getCredit());
            ps.setString(4, c.getTeacher());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("新增课程失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(Course c) {
        String sql = "UPDATE t_course SET course_name = ?, credit = ?, teacher = ? WHERE course_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCourseName());
            ps.setBigDecimal(2, c.getCredit());
            ps.setString(3, c.getTeacher());
            ps.setString(4, c.getCourseNo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("修改课程失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void delete(String courseNo) {
        String sql = "DELETE FROM t_course WHERE course_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseNo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("删除课程失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}
