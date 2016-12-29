package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by loveoh on 2016/12/28.
 */
@WebServlet("/admin/updatenode")
public class AdminUpdateNodeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");

        req.setAttribute("nodeid",nodeid);
        forward("/admin/adminupdatenode",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("type");
        String newNodeName = req.getParameter("nodename");

        TopicService topicService = new TopicService();
        JsonResult jsonResult = new JsonResult();

        try {
            topicService.updateNodeName(nodeid, newNodeName);
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
        }
        renderJSON(jsonResult,resp);
    }
}
