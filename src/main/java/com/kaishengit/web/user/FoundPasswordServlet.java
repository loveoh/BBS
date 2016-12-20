package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by loveoh on 2016/12/17.
 *
 */
@WebServlet("/foundpassword")
public class FoundPasswordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("/user/foundPassword",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        String value = req.getParameter("value");

        //得到客户端的sessionId
        String sessionId = req.getSession().getId();

        UserService userService = new UserService();
        Map<String,Object> result = Maps.newHashMap();
        try {
            userService.findBackPassword(sessionId, type, value);
            result.put("state","success");

        }catch (ServiceException ex){
            result.put("state","error");
            result.put("message",ex.getMessage());
        }
        renderJSON(result,resp);
    }
}
