package com.Jeremy.web.servllet;


import com.Jeremy.domain.Cart;
import com.Jeremy.domain.Product;
import com.Jeremy.service.ProductService;
import com.Jeremy.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

//处理和购物车相关的请求

@WebServlet(name = "CartServlet", urlPatterns = "/cartServlet")
public class CartServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    //将商品添加到购物车中

    public void cartAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**获得目标商品的参数(id和数量)*/
        String pid = request.getParameter("pid");
        String countStr = request.getParameter("count");
        int count = Integer.parseInt(countStr);
        /**根据id查询数据库获得目标商品*/
        ProductService productService = new ProductServiceImpl();
        Product product = null;
        try {
            product = productService.findById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**将目标商品添加到购物车中*/
        Cart cart = getCart(request.getSession());
        cart.addCart(product, count);

        request.getSession().removeAttribute("cart");
        request.getSession().setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
    }

    //删除购物车中的商品

    public void removeItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Cart cart = this.getCart(request.getSession());
        cart.removeCart(pid);

        request.getSession().removeAttribute("cart");
        request.getSession().setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
    }

    //清空购物车
    public void cartClear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = this.getCart(request.getSession());
        cart.clearCart();

        request.getSession().removeAttribute("cart");
        request.getSession().setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
    }

    //Servlet的静态方法，用于查找购物车

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

}
