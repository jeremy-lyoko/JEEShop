package com.Jeremy.web.servllet;

import com.Jeremy.domain.User;
import com.Jeremy.service.UserService;
import com.Jeremy.service.impl.UserServiceImpl;
import com.Jeremy.utils.UUIDUtils;
import com.Jeremy.utils.UserBeanUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

//处理用户信息相关的请求
@WebServlet(name = "UserServlet", urlPatterns = "/userServlet")
public class UserServlet{
    private UserService userService = new UserServiceImpl();

    //用户登录 查询数据库验证用户名和密码
    //如果使用自动登录再加一个cookie
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //获取页面自动登陆选项框的值
        String autoLogin = request.getParameter("autoLogin");
        //自动登录
        if ("1".equals(autoLogin)) {
            /**1.创建一个Cookie用于记住用户的登录名和密码*/
            Cookie autoLoginCookie = new Cookie("autoLoginCookie", username + "@" + password);
            /**2.设置Cookie的路径为当前路径（即用户登陆）*/
            autoLoginCookie.setPath(request.getContextPath());
            /**3.设置Cookie的生命周期为一周*/
            autoLoginCookie.setMaxAge(60 * 60 * 24 * 7);
            /**4.将Cookie发送到浏览器*/
            response.addCookie(autoLoginCookie);
        } else {

            Cookie autoLoginCookie = new Cookie("autoLonginCookie", "");
            autoLoginCookie.setPath(request.getContextPath());
            autoLoginCookie.setMaxAge(0);
            response.addCookie(autoLoginCookie);
        }

        User user = userService.login(username, password);
//验证用户登录
        if (user != null) {
            request.getSession().setAttribute("loginUser", user);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("msg", "用户名或密码不匹配或未激活");
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        }
    }

    //用户退出 删除cookie和session相关内容
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().removeAttribute("loginUser");

        Cookie cookie = new Cookie("autoLoginCookie", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/");
    }

    //用户注册 1.获取用户数据
    //2.将数据存入数据库 3.发送激活邮件
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String usercaption = request.getParameter("usercaption");
        System.out.println(usercaption);

        User user = new User();

        try {
            //将前端发送来的数据传递到user对象中
            UserBeanUtils.populate(user, request.getParameterMap());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        user.setUid(UUIDUtils.getUUID());
        user.setTelephone(null);
        user.setCode(UUIDUtils.getUUID());
        user.setState(0);

        int i = 0;
        try {
            i = userService.regist(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (i > 0) {
            //注册成功后转到登录页面
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");

        } else {
            //出现问题则转到注册页面
            response.sendRedirect(request.getContextPath() + "/jsp/register.jsp");
        }

    }

    //异步校验用户名是否可用
    //查询数据库获得用户名信息

    public void checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        User user = null;

        try {
            user = userService.findByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Boolean isExist = false;

        if (user == null) {
            isExist = false;
        } else if (user != null) {
            isExist = true;
        }
        response.getWriter().write("{\"isExist\":" + isExist + "}");

    }

    //异步校验验证码
    //检验用户输入的验证码是否正确
    public void checkValidatecode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String confirm = request.getParameter("validatecode");

        HttpSession session = request.getSession();
        String word = (String) session.getAttribute("key");
        boolean boole;
        if (confirm.equals(word)) {
            boole = true;
        } else {
            boole = false;
        }
        response.getWriter().write("{\"boole\":" + boole + "}");

    }

    //验证激活码
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("activecode");

        Boolean bool = null;
        try {
            bool = userService.activeUser(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (bool == true) {
            request.setAttribute("msg", "激活成功，请登录");
            response.sendRedirect(request.getContextPath() + "/longin.jsp");
        } else {
            request.setAttribute("msg", "激活失败，请重新发送邮件");
            response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
        }

    }

}
