<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Course" %>
<%
    String mode = (String) request.getAttribute("mode");
    Course course = (Course) request.getAttribute("course");
    boolean edit = "edit".equals(mode);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= edit ? "修改课程" : "添加课程" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2><%= edit ? "修改课程" : "添加课程" %></h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/course">
        <input type="hidden" name="mode" value="<%= edit ? "edit" : "add" %>">
        <div class="form-row">
            <label>课程编号</label>
            <input type="text" name="courseNo" required
                   value="<%= course == null || course.getCourseNo() == null ? "" : course.getCourseNo() %>"
                   <%= edit ? "readonly" : "" %>>
        </div>
        <div class="form-row">
            <label>课程名称</label>
            <input type="text" name="courseName" required
                   value="<%= course == null || course.getCourseName() == null ? "" : course.getCourseName() %>">
        </div>
        <div class="form-row">
            <label>学分</label>
            <input type="number" name="credit" required step="0.5" min="0.5" max="20"
                   value="<%= course == null || course.getCredit() == null ? "" : course.getCredit() %>">
        </div>
        <div class="form-row">
            <label>任课教师</label>
            <input type="text" name="teacher"
                   value="<%= course == null || course.getTeacher() == null ? "" : course.getTeacher() %>">
        </div>
        <button type="submit" class="btn btn-success">保 存</button>
        <a class="btn" href="${pageContext.request.contextPath}/course">返回列表</a>
    </form>
</div>
</body>
</html>
