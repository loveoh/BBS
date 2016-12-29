package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.utils.Page;
import com.kaishengit.vo.UserVo;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.util.JAXBSource;
import java.io.IOException;
import java.util.List;

/**
 * Created by loveoh on 2016/12/29.
 */
@WebServlet("/admin/user")
public class AdminUserServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String p = req.getParameter("page");
        AdminService adminService = new AdminService();
        try {
            Page<UserVo> page = adminService.findUserVoList(p);

            req.setAttribute("page",page);
            forward("/admin/adminuser",req,resp);
        }catch (ServiceException e){
            e.printStackTrace();
            resp.sendError(404,"服务器异常");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid = req.getParameter("id");
        AdminService adminService = new AdminService();
        JsonResult jsonResult = new JsonResult();
        try {
            User user = adminService.findUserByUserId(userid);
            if (user.getStatus() == 1) {
                user.setStatus(2);
                adminService.update(user);
                jsonResult.setState(jsonResult.SUCCESS);
                jsonResult.setData(2);
            } else if (user.getStatus() == 2) {
                user.setStatus(1);
                adminService.update(user);
                jsonResult.setState(jsonResult.SUCCESS);
                jsonResult.setData(1);
            }
        }catch (ServiceException e){
            jsonResult.setMessage(e.getMessage());
        }
        renderJSON(jsonResult,resp);
    }
}
