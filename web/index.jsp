<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页 - 学生成绩管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2>欢迎使用学生成绩管理系统</h2>
    <p style="font-size:14px; line-height:2;">
        本系统基于 JavaWeb（Servlet + JSP + JDBC）与 MySQL 开发，提供以下功能：<br>
        1. <b>学生信息管理</b>：添加、删除、修改、查询全部学生；<br>
        2. <b>课程信息管理</b>：添加、删除、修改、查询全部课程；<br>
        3. <b>成绩管理</b>：录入成绩、修改成绩、按学生/课程查询成绩；<br>
        4. <b>成绩统计分析</b>：按课程计算平均分、最高分、最低分、及格率，并按平均分对学生排名；<br>
        5. <b>数据持久化</b>：所有数据保存于 MySQL 数据库，程序重启后自动加载；<br>
        6. <b>输入校验与异常处理</b>：对非法输入进行友好提示并捕获异常。
    </p>
</div>
</body>
</html>
