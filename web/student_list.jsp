<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sms.entity.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2>学生信息管理</h2>
    <% if (request.getParameter("msg") != null) { %>
    <div class="msg"><%= request.getParameter("msg") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <div class="toolbar">
        <form method="get" action="${pageContext.request.contextPath}/student" style="display:flex;gap:8px;">
            <input type="text" name="keyword" placeholder="学号/姓名/专业/班级"
                   value="<%= request.getAttribute("keyword") == null ? "" : request.getAttribute("keyword") %>">
            <button type="submit" class="btn">查询</button>
        </form>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/student?action=add">＋ 添加学生</a>
    </div>
    <table>
        <tr>
            <th>学号</th><th>姓名</th><th>性别</th><th>年龄</th>
            <th>专业</th><th>班级</th><th>联系电话</th><th>操作</th>
        </tr>
        <%
            List<Student> students = (List<Student>) request.getAttribute("students");
            if (students != null) {
                for (Student s : students) {
        %>
        <tr>
            <td><%= s.getStudentNo() %></td>
            <td><%= s.getName() %></td>
            <td><%= s.getGender() %></td>
            <td><%= s.getAge() %></td>
            <td><%= s.getMajor() %></td>
            <td><%= s.getClassName() %></td>
            <td><%= s.getPhone() == null ? "" : s.getPhone() %></td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/student?action=edit&no=<%= s.getStudentNo() %>">修改</a>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/student?action=delete&no=<%= s.getStudentNo() %>"
                   onclick="return confirm('确定删除该学生及其成绩吗？');">删除</a>
            </td>
        </tr>
        <%      }
           } %>
    </table>
</div>
</body>
</html>
