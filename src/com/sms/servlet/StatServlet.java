package com.sms.servlet;

import com.sms.service.ScoreService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 成绩统计分析控制器（课程统计 + 学生排名）
 */
@WebServlet("/stat")
public class StatServlet extends HttpServlet {

    private final ScoreService scoreService = new ScoreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("courseStats", scoreService.statByCourse());
        req.setAttribute("ranks", scoreService.rankByAvg());
        req.getRequestDispatcher("/stat.jsp").forward(req, resp);
    }
}
