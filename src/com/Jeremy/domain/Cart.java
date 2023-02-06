package com.Jeremy.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

//购物车
public class Cart implements Serializable {
    //map集合储存商品id-商品购买清单
    private Map<String, CartItem> map = new LinkedHashMap<>();
    //总价
    private double total;

    //显示购买清单的集合
    public Collection<CartItem> getCartItems() {
        return map.values();
    }

    //总价total的get方法 遍历map集合的值求和
    public double getTotal() {
        total = 0;
        for (Map.Entry<String, CartItem> entry : map.entrySet()) {
            CartItem cartItem = entry.getValue();
            total += cartItem.getSubtotal();
        }
        return total;
    }

    //添加到购物车
    public void addCart(Product product, int count) {
        if (product == null) {
            return;
        }
        //从商品id找购买清单
        CartItem cartItem = map.get(product.getPid());
        if (cartItem == null) {
            //没有此商品 新建一个放到集合里
            cartItem = new CartItem();
            cartItem.setCount(count);
            cartItem.setProduct(product);
            map.put(product.getPid(), cartItem);
        } else {
            //有此商品 就只添加数量
            cartItem.setCount(cartItem.getCount() + count);
        }
    }

    //从购物车中删除
    public void removeCart(String id) {
        CartItem cartItem = map.remove(id);
        //执行remove同时 记录被remove的清单 并从总价中扣除
        total -= cartItem.getSubtotal();
    }

    //清空
    public void clearCart() {
        map.clear();
        total = 0;
    }
}
