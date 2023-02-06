package com.Jeremy.web.servllet;


import com.Jeremy.domain.Category;
import com.Jeremy.service.CategoryService;
import com.Jeremy.service.impl.CategoryServiceImpl;
import com.Jeremy.utils.UUIDUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


//处理和后台商品分类相关的请求

@WebServlet(name = "AdminCategoryServlet", urlPatterns = "/adminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {

    CategoryService categoryService = new CategoryServiceImpl();

    //查询分类

    public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Category> allCategory = new LinkedList<Category>();
        try {
            allCategory = categoryService.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("allCategory", allCategory);
        response.sendRedirect(request.getContextPath() + "/admin/category/list.jsp");
    }

    //删除分类

    public String categoryRemove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String cid = request.getParameter("cid");
        int i = categoryService.remove(cid);

        return "/adminCategoryServlet?method=categoryList";
    }

    //添加分类

    public String categoryAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String cname = request.getParameter("cname");
        String cid = UUIDUtils.getUUID();
        int i = categoryService.add(cid, cname);

        return "/adminCategoryServlet?method=categoryList";
    }

    //跳转的修改页面

    public void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String cname = request.getParameter("cname");
        String cid = request.getParameter("cid");
        Category category = new Category();
        category.setCname(cname);
        category.setCid(cid);
        request.getSession().setAttribute("categoryItem", category);

        response.sendRedirect(request.getContextPath() + "/admin/category/edit.jsp");
    }

    //修改分类

    public String categoryUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String cname = request.getParameter("cname");
        String cid = request.getParameter("cid");
        int i = categoryService.update(cid, cname);

        return "/adminCategoryServlet?method=categoryList";
    }


}
