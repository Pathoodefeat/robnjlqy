<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sms.entity.Score, com.sms.entity.Student, com.sms.entity.Course" %>
<%
    String mode = (String) request.getAttribute("mode");
    Score score = (Score) request.getAttribute("score");
    boolean edit = "edit".equals(mode);
    List<Student> students = (List<Student>) request.getAttribute("students");
    List<Course> courses = (List<Course>) request.getAttribute("courses");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= edit ? "修改成绩" : "录入成绩" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2><%= edit ? "修改成绩" : "录入成绩" %></h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/score">
        <input type="hidden" name="mode" value="<%= edit ? "edit" : "add" %>">
        <% if (edit) { %>
        <input type="hidden" name="id" value="<%= score.getId() %>">
        <div class="form-row">
            <label>学生</label>
            <span><%= score.getStudentNo() %> <%= score.getStudentName() %></span>
        </div>
        <div class="form-row">
            <label>课程</label>
            <span><%= score.getCourseNo() %> <%= score.getCourseName() %></span>
        </div>
        <% } else { %>
        <div class="form-row">
            <label>学生</label>
            <select name="studentNo" required>
                <% if (students != null) for (Student s : students) { %>
                <option value="<%= s.getStudentNo() %>"><%= s.getStudentNo() %> <%= s.getName() %></option>
                <% } %>
            </select>
        </div>
        <div class="form-row">
            <label>课程</label>
            <select name="courseNo" required>
                <% if (courses != null) for (Course c : courses) { %>
                <option value="<%= c.getCourseNo() %>"><%= c.getCourseNo() %> <%= c.getCourseName() %></option>
                <% } %>
            </select>
        </div>
        <% } %>
        <div class="form-row">
            <label>成绩</label>
            <input type="number" name="score" required step="0.5" min="0" max="100"
                   value="<%= score == null || score.getScore() == null ? "" : score.getScore() %>">
        </div>
        <button type="submit" class="btn btn-success">保 存</button>
        <a class="btn" href="${pageContext.request.contextPath}/score">返回列表</a>
    </form>
</div>
</body>
</html>
