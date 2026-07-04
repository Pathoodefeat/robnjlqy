-- 学生成绩管理系统 数据库建表脚本
DROP DATABASE IF EXISTS sms;
CREATE DATABASE sms DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE sms;

-- 用户表（登录校验）
CREATE TABLE t_user (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(30) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(64) NOT NULL COMMENT '密码',
    role VARCHAR(10) NOT NULL DEFAULT 'admin' COMMENT '角色'
) COMMENT '系统用户表';

-- 学生表
CREATE TABLE t_student (
    student_no VARCHAR(20) PRIMARY KEY COMMENT '学号',
    name VARCHAR(30) NOT NULL COMMENT '姓名',
    gender VARCHAR(2) NOT NULL DEFAULT '男' COMMENT '性别',
    age INT NOT NULL COMMENT '年龄',
    major VARCHAR(50) NOT NULL COMMENT '专业',
    class_name VARCHAR(30) NOT NULL COMMENT '班级',
    phone VARCHAR(20) COMMENT '联系电话',
    CONSTRAINT chk_age CHECK (age BETWEEN 10 AND 60)
) COMMENT '学生信息表';

-- 课程表
CREATE TABLE t_course (
    course_no VARCHAR(20) PRIMARY KEY COMMENT '课程编号',
    course_name VARCHAR(50) NOT NULL COMMENT '课程名称',
    credit DECIMAL(3,1) NOT NULL COMMENT '学分',
    teacher VARCHAR(30) COMMENT '任课教师'
) COMMENT '课程信息表';

-- 成绩表（学生-课程 多对多联系）
CREATE TABLE t_score (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    student_no VARCHAR(20) NOT NULL COMMENT '学号',
    course_no VARCHAR(20) NOT NULL COMMENT '课程编号',
    score DECIMAL(5,2) NOT NULL COMMENT '成绩',
    UNIQUE KEY uk_stu_course (student_no, course_no),
    CONSTRAINT fk_score_student FOREIGN KEY (student_no)
        REFERENCES t_student (student_no) ON DELETE CASCADE,
    CONSTRAINT fk_score_course FOREIGN KEY (course_no)
        REFERENCES t_course (course_no) ON DELETE CASCADE,
    CONSTRAINT chk_score CHECK (score BETWEEN 0 AND 100)
) COMMENT '成绩表';

-- 初始数据
INSERT INTO t_user (username, password, role) VALUES ('admin', 'admin123', 'admin');

INSERT INTO t_student VALUES
('2023001', '张三', '男', 20, '计算机科学与技术', '计科2301', '13800000001'),
('2023002', '李四', '女', 19, '计算机科学与技术', '计科2301', '13800000002'),
('2023003', '王五', '男', 21, '软件工程', '软工2302', '13800000003'),
('2023004', '赵六', '女', 20, '软件工程', '软工2302', '13800000004'),
('2023005', '孙七', '男', 22, '数据科学与大数据技术', '数据2301', '13800000005');

INSERT INTO t_course VALUES
('C001', 'Java面向对象程序设计', 4.0, '陈老师'),
('C002', '数据库原理', 3.5, '刘老师'),
('C003', '数据结构', 4.0, '杨老师');

INSERT INTO t_score (student_no, course_no, score) VALUES
('2023001', 'C001', 88), ('2023001', 'C002', 76), ('2023001', 'C003', 92),
('2023002', 'C001', 95), ('2023002', 'C002', 84),
('2023003', 'C001', 59), ('2023003', 'C003', 67),
('2023004', 'C002', 73), ('2023004', 'C003', 81),
('2023005', 'C001', 66);
