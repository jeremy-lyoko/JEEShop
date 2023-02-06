package com.Jeremy.dao;

import com.Jeremy.domain.Order;
import com.Jeremy.domain.OrderItem;
import com.Jeremy.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//订单相关操作
public interface OrderDao {
    //通过用户id找订单
    int findTotalRecordByUid(User loginUser) throws SQLException;

    List<Order> findAllByUid(User loginUser, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    void save(Connection connection, Order order) throws SQLException;

    void save(Connection connection, OrderItem orderItem) throws SQLException;

    Order findByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    List<Order> orderList(int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    List<Order> findOrderByState(int state, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    int findTotalRecord() throws SQLException;

    int findTotalRecordByState(int state) throws SQLException;

    void outMoney(String name, int money) throws SQLException;

    void inMoney(String to, int money) throws SQLException;

}
