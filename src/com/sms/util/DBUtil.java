package com.sms.util;

import com.sms.exception.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 数据库连接工具类
 */
public class DBUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sms?useUnicode=true&characterEncoding=utf8"
            + "&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL 驱动加载失败: " + e.getMessage());
        }
    }

    private DBUtil() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataAccessException("获取数据库连接失败", e);
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException ignored) {
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ignored) {
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException ignored) {
        }
    }
}
