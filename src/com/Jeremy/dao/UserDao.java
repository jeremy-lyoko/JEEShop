package com.Jeremy.dao;

import com.Jeremy.domain.User;

import java.sql.SQLException;
//对用户数据的操作
public interface UserDao {
    User findByUsername(String username) throws SQLException;

    int InsterUser(User user) throws SQLException;

    User findByCode(String code) throws SQLException;

    void update(User existUser) throws SQLException;

    User findByUid(String username) throws SQLException;

}
