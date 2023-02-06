package com.Jeremy.web.servllet;
import com.google.gson.Gson;
import com.Jeremy.domain.Category;
import com.Jeremy.domain.PageBean;
import com.Jeremy.domain.Product;
import com.Jeremy.service.CategoryService;
import com.Jeremy.service.ProductService;
import com.Jeremy.service.impl.CategoryServiceImpl;
import com.Jeremy.service.impl.ProductServiceImpl;
import com.Jeremy.utils.CookieUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//处理和商品相关的请求

@WebServlet(name = "ProductServlet", urlPatterns = "/productServlet")
public class ProductServlet extends BaseServlet {

    private String cname = null;
    //根据商品分类(cid)查询数据库中的商品，并分页展示在商品展示页(product_list.jsp)

    public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        /**获取商品分类的id*/
        String cid = request.getParameter("cid");

        /**设置当前页的页码(默认是1，即第一页)*/
        int pageNumber = 1;
        String page_number = request.getParameter("pageNumber");
        if (page_number != null) {
            pageNumber = Integer.parseInt(page_number);
        }

        /**设置每页显示4个商品*/
        int pageSize = 4;

        /**调用service层实现类查询商品*/
        ProductService productService = new ProductServiceImpl();
        PageBean<Product> pageBean = null;
        try {
            pageBean = productService.findByCid(cid, pageNumber, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**查询浏览历史*/
        Cookie cookie = CookieUtils.findCookie(request.getCookies(), "history");
        List<Product> list = null;
        if (cookie != null) {
            list = this.browsingHistory(cookie);
        }

        request.getSession().setAttribute("pageBean", pageBean);
        request.getSession().setAttribute("prohis", list);
        response.sendRedirect(request.getContextPath() + "/jsp/product_list.jsp");

    }

    //根据商品关键字查询数据库中的商品，并分页展示在商品展示页(product_search.jsp)
    public void productSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        /**获取商品关键字*/
        String word = request.getParameter("word");

        /**设置当前页的页码(默认是1，即第一页)*/
        int pageNumber = 1;
        String page_number = request.getParameter("pageNumber");
        if (page_number != null) {
            pageNumber = Integer.parseInt(page_number);
        }

        /**设置每页显示4个商品*/
        int pageSize = 4;

        /**调用service层实现类查询商品*/
        ProductService productService = new ProductServiceImpl();
        PageBean<Product> pageBean = null;
        try {
            pageBean = productService.findByWord(word, pageNumber, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**查询浏览历史*/
        Cookie cookie = CookieUtils.findCookie(request.getCookies(), "history");
        List<Product> list = null;
        if (cookie != null) {
            list = this.browsingHistory(cookie);
        }

        request.getSession().setAttribute("pageBean", pageBean);
        request.getSession().setAttribute("prohis", list);
        response.sendRedirect(request.getContextPath() + "/jsp/product_search.jsp");

    }
//查询历史记录中的商品

    public List<Product> browsingHistory(Cookie cookie) throws SQLException {
        String value = cookie.getValue();
        String[] ids = value.split("-");
        ProductService productService = new ProductServiceImpl();
        List<Product> list = new LinkedList<Product>();
        for (String pid : ids) {
            Product product = productService.findById(pid);
            list.add(product);
        }
        return list;
    }

  //根据商品id，查找数据库并展示商品的详细信息
    public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();

        Product product = null;
        try {
            product = productService.findById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**将此商品添加到历史记录中*/
        Cookie cookie = CookieUtils.findCookie(request.getCookies(), "history");
        if (cookie == null) {
            Cookie c = new Cookie("history", pid);
            c.setPath("/");
            c.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(c);
        } else {
            String value = cookie.getValue();
            /**根据"-"分割获取id组成的数组*/
            String[] ids = value.split("-");
            /**字符串数组转换为集合*/
            LinkedList<String> list = new LinkedList<String>(Arrays.asList(ids));
            /**如果此商品已经在浏览历史记录中，则先将其删除后再添加到记录的首位*/
            if (list.contains(pid)) {
                list.remove(pid);
                list.addFirst(pid);
            } else {
                /**如果此商品不在浏览历史记录中，则直接添加到记录的首位。限制历史记录中记录数为6*/
                if (list.size() >= 6) {
                    list.removeLast();
                    list.addFirst(pid);
                } else {
                    list.addFirst(pid);
                }
            }
            StringBuffer sb = new StringBuffer();
            for (String id : list) {
                sb.append(id + "-");
            }

            String history = sb.substring(0, sb.length() - 1);
            Cookie c = new Cookie("history", history);
            c.setPath("/");
            c.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(c);

            request.getSession().removeAttribute("product");
            request.getSession().setAttribute("product", product);
            response.sendRedirect(request.getContextPath() + "/jsp/product_info.jsp");
        }
    }

    //根据关键字查询商品
    public void queryByKeyword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String word = request.getParameter("word");
        ProductService productService = new ProductServiceImpl();
        List<Product> list = null;
        try {
            list = productService.findProductByword(word);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String json = gson.toJson(list);
        response.getWriter().println(json);

    }

    //在首页展示热门商品和新商品

    public void listAtHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = new ProductServiceImpl();
        List<Product> hotList = null;
        List<Product> newList = null;
        try {
            hotList = productService.findByHOt();
            newList = productService.findByNew();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("hotList", hotList);
        request.getSession().setAttribute("newList", newList);

        response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
    }

    //异步查询商品种类，并展示在首页头部

    public void category(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> allCategory = null;
        try {
            allCategory = categoryService.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = gson.toJson(allCategory);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(json);
    }

    public void AJAXTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String txt = "<font>你好</font>";
        response.getWriter().println(txt);

    }


}
