package com.Jeremy.web.filter;

import com.Jeremy.utils.MyRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;


//filter的编码转换
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/encoding")
public class EncodingFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;

        request.setCharacterEncoding("UTF-8");
        MyRequest myRequest = new MyRequest(request);

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
