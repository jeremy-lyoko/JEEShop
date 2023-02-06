package com.Jeremy.service;

import com.Jeremy.domain.User;

import java.sql.SQLException;
//用户业务
//注册激活登录查询
public interface UserService {
    User findByUsername(String username) throws SQLException;

    int regist(User user) throws SQLException;

    Boolean activeUser(String code) throws SQLException;

    User login(String username, String password) throws SQLException;
}
