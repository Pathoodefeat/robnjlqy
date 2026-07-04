package com.sms.servlet;

import com.sms.entity.Course;
import com.sms.exception.BusinessException;
import com.sms.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 课程信息管理控制器（列表/查询/添加/修改/删除）
 */
@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "list" : req.getParameter("action");
        try {
            switch (action) {
                case "add":
                    req.setAttribute("mode", "add");
                    req.getRequestDispatcher("/course_form.jsp").forward(req, resp);
                    break;
                case "edit":
                    req.setAttribute("mode", "edit");
                    req.setAttribute("course", courseService.get(req.getParameter("no")));
                    req.getRequestDispatcher("/course_form.jsp").forward(req, resp);
                    break;
                case "delete":
                    courseService.delete(req.getParameter("no"));
                    resp.sendRedirect(req.getContextPath() + "/course?msg=删除成功");
                    break;
                default:
                    list(req, resp);
            }
        } catch (BusinessException e) {
            req.setAttribute("error", e.getMessage());
            list(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String mode = req.getParameter("mode") == null ? "add" : req.getParameter("mode");
        Course c = new Course();
        c.setCourseNo(req.getParameter("courseNo"));
        c.setCourseName(req.getParameter("courseName"));
        c.setTeacher(req.getParameter("teacher"));
        try {
            c.setCredit(new BigDecimal(req.getParameter("credit")));
            if ("edit".equals(mode)) {
                courseService.update(c);
            } else {
                courseService.add(c);
            }
            resp.sendRedirect(req.getContextPath() + "/course?msg=保存成功");
        } catch (NumberFormatException e) {
            back(req, resp, mode, c, "学分必须为数字");
        } catch (BusinessException e) {
            back(req, resp, mode, c, e.getMessage());
        }
    }

    private void back(HttpServletRequest req, HttpServletResponse resp,
                      String mode, Course c, String error)
            throws ServletException, IOException {
        req.setAttribute("mode", mode);
        req.setAttribute("course", c);
        req.setAttribute("error", error);
        req.getRequestDispatcher("/course_form.jsp").forward(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        req.setAttribute("courses", courseService.list(keyword));
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/course_list.jsp").forward(req, resp);
    }
}
