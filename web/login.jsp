<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录 - 学生成绩管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-box">
    <h2>学生成绩管理系统</h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="text" name="username" placeholder="用户名（admin）" required>
        <input type="password" name="password" placeholder="密码（admin123）" required>
        <button type="submit" class="btn">登 录</button>
    </form>
</div>
</body>
</html>
