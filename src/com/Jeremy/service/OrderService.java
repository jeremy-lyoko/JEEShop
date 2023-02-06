package com.Jeremy.service;

import com.Jeremy.domain.Order;
import com.Jeremy.domain.PageBean;
import com.Jeremy.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
//订单业务处理
public interface OrderService {
    PageBean<Order> findByUid(User loginUser, int pageNumber, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    void save(Order order) throws SQLException;

    Order findByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    PageBean<Order> orderList(int pageNumber, int pageSize) throws IllegalAccessException, SQLException, InvocationTargetException;

    PageBean<Order> findOrderByState(int state, int pageNumber, int pageSize) throws IllegalAccessException, SQLException, InvocationTargetException;

    void transfer(String name, String to, String money) throws SQLException, ClassNotFoundException;
}
