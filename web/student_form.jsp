<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sms.entity.Student" %>
<%
    String mode = (String) request.getAttribute("mode");
    Student stu = (Student) request.getAttribute("student");
    boolean edit = "edit".equals(mode);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= edit ? "修改学生" : "添加学生" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2><%= edit ? "修改学生" : "添加学生" %></h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/student">
        <input type="hidden" name="mode" value="<%= edit ? "edit" : "add" %>">
        <div class="form-row">
            <label>学号</label>
            <input type="text" name="studentNo" required
                   value="<%= stu == null || stu.getStudentNo() == null ? "" : stu.getStudentNo() %>"
                   <%= edit ? "readonly" : "" %>>
        </div>
        <div class="form-row">
            <label>姓名</label>
            <input type="text" name="name" required
                   value="<%= stu == null || stu.getName() == null ? "" : stu.getName() %>">
        </div>
        <div class="form-row">
            <label>性别</label>
            <select name="gender">
                <option value="男" <%= stu != null && "男".equals(stu.getGender()) ? "selected" : "" %>>男</option>
                <option value="女" <%= stu != null && "女".equals(stu.getGender()) ? "selected" : "" %>>女</option>
            </select>
        </div>
        <div class="form-row">
            <label>年龄</label>
            <input type="number" name="age" required min="10" max="60"
                   value="<%= stu == null || stu.getAge() == 0 ? "" : stu.getAge() %>">
        </div>
        <div class="form-row">
            <label>专业</label>
            <input type="text" name="major" required
                   value="<%= stu == null || stu.getMajor() == null ? "" : stu.getMajor() %>">
        </div>
        <div class="form-row">
            <label>班级</label>
            <input type="text" name="className" required
                   value="<%= stu == null || stu.getClassName() == null ? "" : stu.getClassName() %>">
        </div>
        <div class="form-row">
            <label>联系电话</label>
            <input type="text" name="phone"
                   value="<%= stu == null || stu.getPhone() == null ? "" : stu.getPhone() %>">
        </div>
        <button type="submit" class="btn btn-success">保 存</button>
        <a class="btn" href="${pageContext.request.contextPath}/student">返回列表</a>
    </form>
</div>
</body>
</html>
