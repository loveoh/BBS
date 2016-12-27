package com.kaishengit.web.user;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * Created by loveoh on 2016/12/26.
 */
@WebServlet("/notify")
public class NotifyServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        //查询整个notify表,将所有数据都返回到jsp中
        UserService userService = new UserService();
        List<Notify> notifyList = userService.findNotifyListByUser(user);

        req.setAttribute("notifyList",notifyList);
        forward("/user/notify",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        //根据Guava中的collections2.filter来过滤未读信息
        UserService userService = new UserService();
        List<Notify> notifyList = userService.findNotifyListByUser(user);
        List<Notify> unreadNotify =  Lists.newArrayList( Collections2.filter(notifyList, new Predicate<Notify>() {
            @Override
            public boolean apply(Notify notify) {
                //返回所有未读的信息
                return notify.getState() == 0;
            }
        }));

        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(JsonResult.SUCCESS);
        jsonResult.setData(unreadNotify.size());

        renderJSON(jsonResult,resp);
    }
}
