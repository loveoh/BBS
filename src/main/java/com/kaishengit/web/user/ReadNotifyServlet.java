package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by loveoh on 2016/12/27.
 */
@WebServlet("/readnotify")
public class ReadNotifyServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        UserService userService = new UserService();
        userService.updateState(ids);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setState("success");
        renderJSON(jsonResult,resp);
    }
}
