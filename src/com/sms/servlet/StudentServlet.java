package com.sms.servlet;

import com.sms.entity.Student;
import com.sms.exception.BusinessException;
import com.sms.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 学生信息管理控制器（列表/查询/添加/修改/删除）
 */
@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = param(req, "action", "list");
        try {
            switch (action) {
                case "add":
                    req.setAttribute("mode", "add");
                    req.getRequestDispatcher("/student_form.jsp").forward(req, resp);
                    break;
                case "edit":
                    req.setAttribute("mode", "edit");
                    req.setAttribute("student", studentService.get(req.getParameter("no")));
                    req.getRequestDispatcher("/student_form.jsp").forward(req, resp);
                    break;
                case "delete":
                    studentService.delete(req.getParameter("no"));
                    resp.sendRedirect(req.getContextPath() + "/student?msg=删除成功");
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
        String mode = param(req, "mode", "add");
        Student s = new Student();
        s.setStudentNo(req.getParameter("studentNo"));
        s.setName(req.getParameter("name"));
        s.setGender(req.getParameter("gender"));
        s.setMajor(req.getParameter("major"));
        s.setClassName(req.getParameter("className"));
        s.setPhone(req.getParameter("phone"));
        try {
            s.setAge(Integer.parseInt(param(req, "age", "0")));
            if ("edit".equals(mode)) {
                studentService.update(s);
            } else {
                studentService.add(s);
            }
            resp.sendRedirect(req.getContextPath() + "/student?msg=保存成功");
        } catch (NumberFormatException e) {
            back(req, resp, mode, s, "年龄必须为整数");
        } catch (BusinessException e) {
            back(req, resp, mode, s, e.getMessage());
        }
    }

    private void back(HttpServletRequest req, HttpServletResponse resp,
                      String mode, Student s, String error)
            throws ServletException, IOException {
        req.setAttribute("mode", mode);
        req.setAttribute("student", s);
        req.setAttribute("error", error);
        req.getRequestDispatcher("/student_form.jsp").forward(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        req.setAttribute("students", studentService.list(keyword));
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/student_list.jsp").forward(req, resp);
    }

    private String param(HttpServletRequest req, String name, String def) {
        String v = req.getParameter(name);
        return v == null || v.isEmpty() ? def : v;
    }
}
