package com.kaishengit.web.admin;

import com.kaishengit.service.AdminService;
import com.kaishengit.utils.Page;
import com.kaishengit.vo.TopicVo;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by loveoh on 2016/12/28.
 */
@WebServlet("/admin/home")
public class AdminHomeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageNo = req.getParameter("page");
        pageNo = StringUtils.isNumeric(pageNo) ? pageNo : "1";
        AdminService adminService  = new AdminService();
        Page<TopicVo> page = adminService.findTopicVo(Integer.valueOf(pageNo));
        req.setAttribute("page",page);
        forward("/admin/adminhome",req,resp);
    }
}
