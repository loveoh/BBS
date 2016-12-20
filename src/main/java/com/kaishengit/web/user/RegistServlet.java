package com.kaishengit.web.user;

import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by loveoh on 2016/12/15.
 */
@WebServlet("/regist")
public class RegistServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("/user/regist",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        Map<String ,Object> result = new HashMap<>();

        try {
            UserService userService = new UserService();
            userService.add(username, password, email, phone);
            result.put("state","success");

        }catch (Exception e){
           e.printStackTrace();
            result.put("state","error");
            result.put("message","注册失败");
        }
        renderJSON(result,resp);
    }
}
