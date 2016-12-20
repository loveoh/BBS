package com.kaishengit.web.user.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by loveoh on 2016/12/19.
 */
public class LoginFilter extends AbstractFilter {

    private static List<String> urlList = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String validateUrl = filterConfig.getInitParameter("ValidateUrl");
        urlList = Arrays.asList(validateUrl.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取用户访问的url
        String url = request.getRequestURI();
        System.out.println(url);

        if (urlList != null && urlList.contains(url)){
            if (request.getSession().getAttribute("curr_user") == null ){
                response.sendRedirect("/login?redirect="+url);
            }else {
                filterChain.doFilter(request,response);
            }
        }else {
            filterChain.doFilter(request,response);
        }


    }
}
