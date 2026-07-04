package com.sms.service;

import com.sms.dao.UserDao;
import com.sms.entity.User;
import com.sms.exception.BusinessException;

/**
 * 用户业务逻辑层：登录校验
 */
public class UserService {

    private final UserDao userDao = new UserDao();

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            throw new BusinessException("用户名和密码不能为空");
        }
        User user = userDao.findByUsername(username.trim());
        if (user == null || !user.getPassword().equals(password)) {
            throw new BusinessException("用户名或密码错误");
        }
        return user;
    }
}
