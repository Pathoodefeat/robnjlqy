package com.sms.dao;

import com.sms.entity.CourseStat;
import com.sms.entity.Score;
import com.sms.entity.StudentRank;
import com.sms.exception.DataAccessException;
import com.sms.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 成绩数据访问对象（增删改查 + 统计分析）
 */
public class ScoreDao {

    private static final String BASE_SELECT =
            "SELECT sc.id, sc.student_no, sc.course_no, sc.score, st.name AS student_name, c.course_name "
            + "FROM t_score sc "
            + "JOIN t_student st ON sc.student_no = st.student_no "
            + "JOIN t_course c ON sc.course_no = c.course_no ";

    private Score mapRow(ResultSet rs) throws SQLException {
        Score s = new Score();
        s.setId(rs.getInt("id"));
        s.setStudentNo(rs.getString("student_no"));
        s.setCourseNo(rs.getString("course_no"));
        s.setScore(rs.getBigDecimal("score"));
        s.setStudentName(rs.getString("student_name"));
        s.setCourseName(rs.getString("course_name"));
        return s;
    }

    /** 按学号/课程号条件查询成绩列表 */
    public List<Score> find(String studentNo, String courseNo) {
        StringBuilder sql = new StringBuilder(BASE_SELECT + "WHERE 1=1");
        List<String> params = new ArrayList<>();
        if (studentNo != null && !studentNo.trim().isEmpty()) {
            sql.append(" AND sc.student_no = ?");
            params.add(studentNo.trim());
        }
        if (courseNo != null && !courseNo.trim().isEmpty()) {
            sql.append(" AND sc.course_no = ?");
            params.add(courseNo.trim());
        }
        sql.append(" ORDER BY sc.student_no, sc.course_no");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            List<Score> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("查询成绩失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    public Score findById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(BASE_SELECT + "WHERE sc.id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        } catch (SQLException e) {
            throw new DataAccessException("查询成绩失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    public boolean exists(String studentNo, String courseNo) {
        String sql = "SELECT 1 FROM t_score WHERE student_no = ? AND course_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentNo);
            ps.setString(2, courseNo);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DataAccessException("查询成绩失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    public void insert(Score s) {
        String sql = "INSERT INTO t_score (student_no, course_no, score) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getStudentNo());
            ps.setString(2, s.getCourseNo());
            ps.setBigDecimal(3, s.getScore());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("录入成绩失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(int id, BigDecimal score) {
        String sql = "UPDATE t_score SET score = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, score);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("修改成绩失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM t_score WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("删除成绩失败", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    /** 各课程统计：平均分、最高分、最低分、及格率 */
    public List<CourseStat> statByCourse() {
        String sql = "SELECT c.course_no, c.course_name, COUNT(sc.id) AS cnt, "
                + "ROUND(AVG(sc.score), 2) AS avg_score, MAX(sc.score) AS max_score, "
                + "MIN(sc.score) AS min_score, "
                + "ROUND(SUM(CASE WHEN sc.score >= 60 THEN 1 ELSE 0 END) * 100.0 / COUNT(sc.id), 2) AS pass_rate "
                + "FROM t_course c JOIN t_score sc ON c.course_no = sc.course_no "
                + "GROUP BY c.course_no, c.course_name ORDER BY c.course_no";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<CourseStat> list = new ArrayList<>();
            while (rs.next()) {
                CourseStat st = new CourseStat();
                st.setCourseNo(rs.getString("course_no"));
                st.setCourseName(rs.getString("course_name"));
                st.setCount(rs.getInt("cnt"));
                st.setAvgScore(rs.getBigDecimal("avg_score"));
                st.setMaxScore(rs.getBigDecimal("max_score"));
                st.setMinScore(rs.getBigDecimal("min_score"));
                st.setPassRate(rs.getBigDecimal("pass_rate"));
                list.add(st);
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("课程统计失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }

    /** 学生排名：按平均分降序 */
    public List<StudentRank> rankByAvg() {
        String sql = "SELECT st.student_no, st.name, COUNT(sc.id) AS cnt, "
                + "SUM(sc.score) AS total_score, ROUND(AVG(sc.score), 2) AS avg_score "
                + "FROM t_student st JOIN t_score sc ON st.student_no = sc.student_no "
                + "GROUP BY st.student_no, st.name ORDER BY avg_score DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<StudentRank> list = new ArrayList<>();
            int rank = 1;
            while (rs.next()) {
                StudentRank r = new StudentRank();
                r.setRank(rank++);
                r.setStudentNo(rs.getString("student_no"));
                r.setStudentName(rs.getString("name"));
                r.setCourseCount(rs.getInt("cnt"));
                r.setTotalScore(rs.getBigDecimal("total_score"));
                r.setAvgScore(rs.getBigDecimal("avg_score"));
                list.add(r);
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("学生排名统计失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }
}
