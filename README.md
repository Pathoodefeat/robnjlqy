# 学生成绩管理系统（JavaWeb + MySQL）

《Java 面向对象程序设计实训》课程项目。基于 Servlet + JSP + JDBC（不使用 SpringBoot）与 MySQL 开发的学生成绩管理系统。

## 功能

1. **登录/注销**：账号 `admin` / 密码 `admin123`，未登录访问自动跳转登录页（过滤器实现）
2. **学生信息管理**：添加、删除、修改、查询全部学生（支持学号/姓名/专业/班级模糊查询）
3. **课程信息管理**：添加、删除、修改、查询全部课程
4. **成绩管理**：录入成绩、修改成绩、按学生/课程查询成绩
5. **成绩统计分析**：按课程计算平均分、最高分、最低分、及格率；按平均分对学生排名
6. **数据持久化**：所有数据保存于 MySQL，程序重启自动加载
7. **输入校验与异常处理**：自定义异常 `BusinessException` / `DataAccessException`，对非法输入友好提示

## 技术栈

- Java 17（兼容 Java 8+ 语法）
- Servlet 4.0 + JSP（Tomcat 9）
- JDBC + MySQL（mysql-connector-j 8.4.0）

## 目录结构

```
sms/
├── sql/schema.sql          # 建库建表脚本（含初始数据）
├── src/com/sms/
│   ├── entity/             # 实体类：Student, Course, Score, User, CourseStat, StudentRank
│   ├── dao/                # 数据访问层（JDBC）
│   ├── service/            # 业务逻辑层（输入校验、业务规则）
│   ├── servlet/            # 控制器：Login/Student/Course/Score/Stat
│   ├── filter/             # 编码过滤器、登录校验过滤器
│   ├── util/DBUtil.java    # JDBC 连接工具
│   └── exception/          # 自定义异常
└── web/                    # JSP 页面、css、WEB-INF
```

## 部署运行

1. 安装 MySQL，执行 `sql/schema.sql` 初始化数据库（默认连接 `root` 无密码，可在 `DBUtil.java` 修改）
2. 编译：
   ```
   javac -encoding UTF-8 -cp "<tomcat>/lib/servlet-api.jar;web/WEB-INF/lib/mysql-connector-j-8.4.0.jar" -d web/WEB-INF/classes <src下全部java文件>
   ```
3. 将 `web/` 目录复制为 Tomcat `webapps/sms/`，启动 Tomcat
4. 访问 `http://localhost:8080/sms/`，使用 admin / admin123 登录
