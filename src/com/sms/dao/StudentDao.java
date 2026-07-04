package com.sms.dao;

import com.sms.entity.Student;
import com.sms.exception.DataAccessException;
import com.sms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生数据访问对象（JDBC 实现增删改查）
 */
public class StudentDao {

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("student_no"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getInt("age"),
                rs.getString("major"),
                rs.getString("class_name"),
                rs.getString("phone"));
    }

    public List<Student> findAll(String keyword) {
        StringBuilder sql = new StringBuilder(
                "SELECT student_no, name, gender, age, major, class_name, phone FROM t_student");
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        if (hasKeyword) {
            sql.append(" WHERE student_no LIKE ? OR name LIKE ? OR major LIKE ? OR class_name LIKE ?");
        }
        sql.append(" ORDER BY student_no");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            if (hasKeyword) {
                String like = "%" + keyword.trim() + "%";
                for (int i = 1; i <= 4; i++) {
                    ps.setString(i, like);
                }
            }
            rs = ps.executeQuery();
            List<Student> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("查询学生列表失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    public Student findByNo(String studentNo) {
        String sql = "SELECT student_no, name, gender, age, major, class_name, phone "
                + "FROM t_student WHERE student_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentNo);
            rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        } catch (SQLException e) {
            throw new DataAccessException("查询学生失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    public void insert(Student s) {
        String sql = "INSERT INTO t_student (student_no, name, gender, age, major, class_name, phone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getStudentNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getGender());
            ps.setInt(4, s.getAge());
            ps.setString(5, s.getMajor());
            ps.setString(6, s.getClassName());
            ps.setString(7, s.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("新增学生失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(Student s) {
        String sql = "UPDATE t_student SET name = ?, gender = ?, age = ?, major = ?, "
                + "class_name = ?, phone = ? WHERE student_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getGender());
            ps.setInt(3, s.getAge());
            ps.setString(4, s.getMajor());
            ps.setString(5, s.getClassName());
            ps.setString(6, s.getPhone());
            ps.setString(7, s.getStudentNo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("修改学生失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void delete(String studentNo) {
        String sql = "DELETE FROM t_student WHERE student_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentNo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("删除学生失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}
