package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.utils.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by loveoh on 2016/12/19.
 */
@WebServlet("/user/setting")
public class SettingServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //先获取七牛的token
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
        String token = auth.uploadToken(Config.get("qiniu.bucket"));
        req.setAttribute("token",token);

        forward("/user/setting",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if ("profile".equals(req.getParameter("action"))){
            updateProfile(req,resp);//修改邮箱
        }else if ("password".equals(req.getParameter("action"))){
            updatePassword(req,resp);//修改密码
        }else if("avatar".equals(req.getParameter("action"))){
            updateAvatar(req,resp);//修改头像
        }else{
            throw new ServiceException("服务端异常");
        }

    }


    private void updateAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String avatar = req.getParameter("fileKey");
        User user = getCurrentUser(req);
        user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
        UserService userService = new UserService();
        userService.uapdateAvatar(user,avatar);

        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(JsonResult.SUCCESS);
        renderJSON(jsonResult,resp);

    }

    private void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        User user = getCurrentUser(req);


        UserService userService = new UserService();
        try {
            userService.updatePassword(user, oldPassword, newPassword);
            JsonResult jsonResult = new JsonResult();
            jsonResult.setState(jsonResult.SUCCESS);

            renderJSON(jsonResult,resp);
        }catch (ServiceException ex){
            JsonResult jsonResult = new JsonResult(ex.getMessage());
            renderJSON(jsonResult,resp);
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        User user = getCurrentUser(req);

        UserService userService = new UserService();
        userService.updateEmail(user,email);

        Map<String,Object> result = Maps.newHashMap();
        result.put("state","success");
        renderJSON(result,resp);

    }
}
