package com.Jeremy.service.impl;

import com.Jeremy.dao.OrderDao;
import com.Jeremy.dao.impl.OrderDaoImpl;
import com.Jeremy.domain.Order;
import com.Jeremy.domain.OrderItem;
import com.Jeremy.domain.PageBean;
import com.Jeremy.domain.User;
import com.Jeremy.service.OrderService;
import com.Jeremy.utils.DataSourceUtils;
import com.Jeremy.utils.JDBCUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//订单处理的实现类
public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();

    //分页查询订单
    @Override
    public PageBean<Order> findByUid(User loginUser, int pageNumber, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException {
        //查询数据库中总订单数
        int totalRecord = orderDao.findTotalRecordByUid(loginUser);
        PageBean<Order> pageBean = new PageBean<>(pageNumber, pageSize, totalRecord);
        //利用pageBean的构造函数计算startIndex和pageSize
        //分页查询得到数据库数据传递到pageBean
        List<Order> data = orderDao.findAllByUid(loginUser, pageBean.getStartIndex(), pageBean.getPageSize());
        pageBean.setData(data);
        return pageBean;
    }

    //保存
    @Override
    public void save(Order order) throws SQLException {
        Connection connection = DataSourceUtils.startTransaction();
        orderDao.save(connection, order);
        for (OrderItem orderItem : order.getOrderItems()) {
            orderDao.save(connection, orderItem);
        }
        DataSourceUtils.commitAndRelease();
    }

    //根据oid查找订单
    @Override
    public Order findByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        Order order = orderDao.findByOid(oid);
        return order;
    }

    //显示所有订单
    @Override
    public PageBean<Order> orderList(int pageNumber, int pageSize) throws IllegalAccessException, SQLException, InvocationTargetException {
        int totalRecord = orderDao.findTotalRecord();
        PageBean pageBean = new PageBean<>(pageNumber, pageSize, totalRecord);
        List<Order> list = orderDao.orderList(pageBean.getStartIndex(), pageBean.getPageSize());
        pageBean.setData(list);
        return pageBean;
    }

    //商城后台按照订单状态显示订单
    @Override
    public PageBean<Order> findOrderByState(int state, int pageNumber, int pageSize) throws IllegalAccessException, SQLException, InvocationTargetException {
        int totalRecord = orderDao.findTotalRecordByState(state);

        PageBean pageBean = new PageBean(pageNumber, pageSize, totalRecord);

        List<Order> list = orderDao.findOrderByState(state, pageBean.getStartIndex(), pageBean.getPageSize());

        pageBean.setData(list);

        return pageBean;
    }

    @Override
    public void transfer(String name, String to, String money) throws SQLException, ClassNotFoundException {
        int money_int = Integer.parseInt(money);
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            //开始事务
            connection.setAutoCommit(false);

            orderDao.outMoney(name, money_int);

            orderDao.inMoney(to, money_int);

            connection.commit();
        } catch (Exception e) {
            try {
                if (connection != null) {
                    //发生错误则事务回滚
                    connection.rollback();
                }
            } catch (Exception e2) {

            }
            throw new RuntimeException(e);
        } finally {
            //关闭数据库资源
            JDBCUtils.closeResouce(connection, null, null);
        }
    }


}
