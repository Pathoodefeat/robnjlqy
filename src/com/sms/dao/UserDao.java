package com.sms.dao;

import com.sms.entity.User;
import com.sms.exception.DataAccessException;
import com.sms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户数据访问对象
 */
public class UserDao {

    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, role FROM t_user WHERE username = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("查询用户失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }
}
