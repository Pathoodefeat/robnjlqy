package com.sms.servlet;

import com.sms.exception.BusinessException;
import com.sms.service.CourseService;
import com.sms.service.ScoreService;
import com.sms.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 成绩管理控制器（录入/修改/删除/按学生/课程查询）
 */
@WebServlet("/score")
public class ScoreServlet extends HttpServlet {

    private final ScoreService scoreService = new ScoreService();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "list" : req.getParameter("action");
        try {
            switch (action) {
                case "add":
                    req.setAttribute("mode", "add");
                    prepareForm(req);
                    req.getRequestDispatcher("/score_form.jsp").forward(req, resp);
                    break;
                case "edit":
                    req.setAttribute("mode", "edit");
                    req.setAttribute("score", scoreService.get(Integer.parseInt(req.getParameter("id"))));
                    prepareForm(req);
                    req.getRequestDispatcher("/score_form.jsp").forward(req, resp);
                    break;
                case "delete":
                    scoreService.delete(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect(req.getContextPath() + "/score?msg=删除成功");
                    break;
                default:
                    list(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "参数格式错误");
            list(req, resp);
        } catch (BusinessException e) {
            req.setAttribute("error", e.getMessage());
            list(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String mode = req.getParameter("mode") == null ? "add" : req.getParameter("mode");
        try {
            BigDecimal score = new BigDecimal(req.getParameter("score"));
            if ("edit".equals(mode)) {
                scoreService.update(Integer.parseInt(req.getParameter("id")), score);
            } else {
                scoreService.add(req.getParameter("studentNo"), req.getParameter("courseNo"), score);
            }
            resp.sendRedirect(req.getContextPath() + "/score?msg=保存成功");
        } catch (NumberFormatException e) {
            back(req, resp, mode, "成绩必须为数字");
        } catch (BusinessException e) {
            back(req, resp, mode, e.getMessage());
        }
    }

    private void back(HttpServletRequest req, HttpServletResponse resp, String mode, String error)
            throws ServletException, IOException {
        req.setAttribute("mode", mode);
        req.setAttribute("error", error);
        prepareForm(req);
        req.getRequestDispatcher("/score_form.jsp").forward(req, resp);
    }

    private void prepareForm(HttpServletRequest req) {
        req.setAttribute("students", studentService.list(null));
        req.setAttribute("courses", courseService.list(null));
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String studentNo = req.getParameter("studentNo");
        String courseNo = req.getParameter("courseNo");
        req.setAttribute("scores", scoreService.query(studentNo, courseNo));
        req.setAttribute("students", studentService.list(null));
        req.setAttribute("courses", courseService.list(null));
        req.setAttribute("qStudentNo", studentNo);
        req.setAttribute("qCourseNo", courseNo);
        req.getRequestDispatcher("/score_list.jsp").forward(req, resp);
    }
}
