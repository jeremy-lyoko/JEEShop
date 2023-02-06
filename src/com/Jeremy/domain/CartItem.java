package com.Jeremy.domain;

import java.io.Serializable;

//商品清单(数量/产品名/总价)
public class CartItem implements Serializable {
    private Product product;
    private int count;
    private double subtotal;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //总价计算得到 私有变量没有设置set方法
    public double getSubtotal() {
        this.subtotal = count * product.getShop_price();
        return subtotal;
    }


}
