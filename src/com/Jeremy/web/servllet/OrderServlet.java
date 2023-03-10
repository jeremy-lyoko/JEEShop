package com.Jeremy.web.servllet;

import com.Jeremy.domain.*;
import com.Jeremy.service.OrderService;
import com.Jeremy.service.impl.OrderServiceImpl;
import com.Jeremy.utils.UUIDUtils;
import com.Jeremy.web.servllet.BaseServlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

//处理和订单相关的请求

@WebServlet(name = "OrderServlet", urlPatterns = "/orderServlet")
public class OrderServlet extends BaseServlet {
    OrderService orderService = new OrderServiceImpl();

    public void findByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, SQLException, InvocationTargetException {
        /**设置页码*/
        int pageNumber = 1;
        String number = request.getParameter("pageNumber");
        if (number != null) {
            pageNumber = Integer.parseInt(number);
        }
        /**每页显示数(暂定)*/
        int pageSize = 3;

        User loginUser = (User) request.getSession().getAttribute("loginUser");

        PageBean<Order> pageBean = orderService.findByUid(loginUser, pageNumber, pageSize);

        request.getSession().setAttribute("orderList", pageBean);

        response.sendRedirect(request.getContextPath() + "/jsp/order_list.jsp");
    }

    //根据购物车中的商品创建订单

    public void addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.getSession().setAttribute("msg", "请登陆后继续购买商品");
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        }
        Order order = new Order();
        order.setOid(UUIDUtils.getUUID());
        order.setState(1);
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());
        order.setUser(loginUser);
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemid(UUIDUtils.getUUID());
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        orderService.save(order);

        cart.clearCart();
        request.getSession().setAttribute("order", order);
        response.sendRedirect(request.getContextPath() + "/jsp/order_info.jsp");
    }

    //根据oid数据库显示订单详情
    public void orderInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException {
        String oid = request.getParameter("oid");
        Order order = orderService.findByOid(oid);
        request.getSession().setAttribute("order", order);
        response.sendRedirect(request.getContextPath() + "/jsp/order_info.jsp");
    }

    public void orderPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException {

        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String pd_FrpId = request.getParameter("pd_FrpId");
        String money = request.getParameter("money");
        /**System.out.println(address+".."+name+".."+telephone+".."+pd_FrpId);*/

        request.getSession().setAttribute("name", name);
        request.getSession().setAttribute("to", pd_FrpId);
        request.getSession().setAttribute("money", money);

        response.sendRedirect(request.getContextPath() + "/jsp/transfer.jsp");
    }

    public void transfer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        String name = request.getParameter("name");
        String to = request.getParameter("to");
        String money = request.getParameter("money");

        String[] strings = new String[2];
        //"."和"|"都是转义字符，在使用split函数分割字符串时，在两符号前加\\*/
        strings = money.split("\\.", 2);
        System.out.println(strings[0]);
        orderService.transfer(name, to, strings[0]);

        response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
    }


}
