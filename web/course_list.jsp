<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sms.entity.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>课程管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2>课程信息管理</h2>
    <% if (request.getParameter("msg") != null) { %>
    <div class="msg"><%= request.getParameter("msg") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <div class="toolbar">
        <form method="get" action="${pageContext.request.contextPath}/course" style="display:flex;gap:8px;">
            <input type="text" name="keyword" placeholder="课程编号/名称/教师"
                   value="<%= request.getAttribute("keyword") == null ? "" : request.getAttribute("keyword") %>">
            <button type="submit" class="btn">查询</button>
        </form>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/course?action=add">＋ 添加课程</a>
    </div>
    <table>
        <tr>
            <th>课程编号</th><th>课程名称</th><th>学分</th><th>任课教师</th><th>操作</th>
        </tr>
        <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            if (courses != null) {
                for (Course c : courses) {
        %>
        <tr>
            <td><%= c.getCourseNo() %></td>
            <td><%= c.getCourseName() %></td>
            <td><%= c.getCredit() %></td>
            <td><%= c.getTeacher() == null ? "" : c.getTeacher() %></td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/course?action=edit&no=<%= c.getCourseNo() %>">修改</a>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/course?action=delete&no=<%= c.getCourseNo() %>"
                   onclick="return confirm('确定删除该课程及其成绩吗？');">删除</a>
            </td>
        </tr>
        <%      }
           } %>
    </table>
</div>
</body>
</html>
