<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sms.entity.Score, com.sms.entity.Student, com.sms.entity.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成绩管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2>成绩管理</h2>
    <% if (request.getParameter("msg") != null) { %>
    <div class="msg"><%= request.getParameter("msg") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <div class="toolbar">
        <form method="get" action="${pageContext.request.contextPath}/score" style="display:flex;gap:8px;">
            <%
                String qStu = (String) request.getAttribute("qStudentNo");
                String qCourse = (String) request.getAttribute("qCourseNo");
                List<Student> students = (List<Student>) request.getAttribute("students");
                List<Course> courses = (List<Course>) request.getAttribute("courses");
            %>
            <select name="studentNo">
                <option value="">-- 全部学生 --</option>
                <% if (students != null) for (Student s : students) { %>
                <option value="<%= s.getStudentNo() %>"
                        <%= s.getStudentNo().equals(qStu) ? "selected" : "" %>>
                    <%= s.getStudentNo() %> <%= s.getName() %>
                </option>
                <% } %>
            </select>
            <select name="courseNo">
                <option value="">-- 全部课程 --</option>
                <% if (courses != null) for (Course c : courses) { %>
                <option value="<%= c.getCourseNo() %>"
                        <%= c.getCourseNo().equals(qCourse) ? "selected" : "" %>>
                    <%= c.getCourseNo() %> <%= c.getCourseName() %>
                </option>
                <% } %>
            </select>
            <button type="submit" class="btn">查询</button>
        </form>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/score?action=add">＋ 录入成绩</a>
    </div>
    <table>
        <tr>
            <th>序号</th><th>学号</th><th>姓名</th><th>课程编号</th><th>课程名称</th><th>成绩</th><th>操作</th>
        </tr>
        <%
            List<Score> scores = (List<Score>) request.getAttribute("scores");
            if (scores != null) {
                int i = 1;
                for (Score sc : scores) {
        %>
        <tr>
            <td><%= i++ %></td>
            <td><%= sc.getStudentNo() %></td>
            <td><%= sc.getStudentName() %></td>
            <td><%= sc.getCourseNo() %></td>
            <td><%= sc.getCourseName() %></td>
            <td><%= sc.getScore() %></td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/score?action=edit&id=<%= sc.getId() %>">修改</a>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/score?action=delete&id=<%= sc.getId() %>"
                   onclick="return confirm('确定删除该成绩记录吗？');">删除</a>
            </td>
        </tr>
        <%      }
           } %>
    </table>
</div>
</body>
</html>
