package com.Jeremy.service.impl;

import com.Jeremy.dao.UserDao;
import com.Jeremy.dao.impl.UserDaoImpl;
import com.Jeremy.domain.User;
import com.Jeremy.service.UserService;

import java.sql.SQLException;

//用户业务实现类
public class UserServiceImpl implements UserService {
    //先从数据层调对象
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User findByUsername(String username) throws SQLException {
        User user = userDao.findByUsername(username);
        return user;
    }

    @Override
    public int regist(User user) throws SQLException {
        int i = userDao.InsterUser(user);
        return i;
    }

    @Override
    public Boolean activeUser(String code) throws SQLException {
        User existUser = userDao.findByCode(code);

        if (existUser != null) {
            existUser.setState(1);
            existUser.setCode(null);
            userDao.update(existUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(String username, String password) throws SQLException {
        User user = findByUsername(username);

        if (password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
