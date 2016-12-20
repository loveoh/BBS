package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by loveoh on 2016/12/17.
 */
@WebServlet("/user/resetpassword")
public class ResetPasswordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (token == null) {
            resp.sendError(404);
        } else {
            UserService userService = new UserService();

            try {
                User user = userService.findByToken(token);
                req.setAttribute("token",token);
                req.setAttribute("id",user.getId());

                forward("user/resetpassword", req, resp);

            }catch (ServiceException ex){
                req.setAttribute("message",ex.getMessage());
                forward("user/reseterror",req,resp);
               throw new ServiceException("账户不存在");

            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String id = req.getParameter("id");
        String token = req.getParameter("token");

        UserService userService = new UserService();
        Map<String, Object> result = Maps.newHashMap();

        try {
            userService.resetPassword(password,Integer.valueOf(id), token);
            result.put("state", "success");

        } catch (ServiceException ex) {
            result.put("state", "error");
            result.put("message", ex.getMessage());
        }
        renderJSON(result, resp);
    }
}
