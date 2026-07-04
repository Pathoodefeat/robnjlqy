<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sms.entity.CourseStat, com.sms.entity.StudentRank" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>统计分析</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jspf" %>
<div class="container">
    <h2>成绩统计分析</h2>

    <h3>一、课程成绩统计（平均分 / 最高分 / 最低分 / 及格率）</h3>
    <table>
        <tr>
            <th>课程编号</th><th>课程名称</th><th>人数</th>
            <th>平均分</th><th>最高分</th><th>最低分</th><th>及格率(%)</th>
        </tr>
        <%
            List<CourseStat> stats = (List<CourseStat>) request.getAttribute("courseStats");
            if (stats != null) for (CourseStat st : stats) {
        %>
        <tr>
            <td><%= st.getCourseNo() %></td>
            <td><%= st.getCourseName() %></td>
            <td><%= st.getCount() %></td>
            <td><%= st.getAvgScore() %></td>
            <td><%= st.getMaxScore() %></td>
            <td><%= st.getMinScore() %></td>
            <td><%= st.getPassRate() %></td>
        </tr>
        <% } %>
    </table>

    <h3>二、学生成绩排名（按平均分降序）</h3>
    <table>
        <tr>
            <th>名次</th><th>学号</th><th>姓名</th><th>课程数</th><th>总分</th><th>平均分</th>
        </tr>
        <%
            List<StudentRank> ranks = (List<StudentRank>) request.getAttribute("ranks");
            if (ranks != null) for (StudentRank r : ranks) {
        %>
        <tr>
            <td><%= r.getRank() %></td>
            <td><%= r.getStudentNo() %></td>
            <td><%= r.getStudentName() %></td>
            <td><%= r.getCourseCount() %></td>
            <td><%= r.getTotalScore() %></td>
            <td><%= r.getAvgScore() %></td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
