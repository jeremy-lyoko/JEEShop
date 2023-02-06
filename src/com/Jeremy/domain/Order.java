package com.Jeremy.domain;

import javax.jws.soap.SOAPBinding;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//订单类
public class Order implements Serializable {
    private String oid;
    //下单时间
    private Date ordertime;
    //总金额
    private double total;
    //state订单支付状态，1代表未付款，
    //2代表已付款未发货，3代表已发货为收货，4代表收货订单结束
    private int state;
    private String address;
    private String name;
    private String telephone;
    private User user;
    List<OrderItem> orderItems= new ArrayList<>();
    /**
     * 获取
     * @return oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * 设置
     * @param oid
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * 获取
     * @return ordertime
     */
    public Date getOrdertime() {
        return ordertime;
    }

    /**
     * 设置
     * @param ordertime
     */
    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    /**
     * 获取
     * @return total
     */
    public double getTotal() {
        return total;
    }

    /**
     * 设置
     * @param total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * 获取
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * 获取
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取
     * @return orderItems
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * 设置
     * @param orderItems
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String toString() {
        return "Order{oid = " + oid + ", ordertime = " + ordertime + ", total = " + total + ", state = " + state + ", address = " + address + ", name = " + name + ", telephone = " + telephone + ", user = " + user + ", orderItems = " + orderItems + "}";
    }
}
