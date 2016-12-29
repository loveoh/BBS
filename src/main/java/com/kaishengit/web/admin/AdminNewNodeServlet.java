package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by loveoh on 2016/12/28.
 */
@WebServlet("/admin/newnode")
public class AdminNewNodeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("/admin/adminnewnode",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeName = req.getParameter("nodename");
        //转码
        //nodeName = StringUtils.iosChangeToUtf8(nodeName);
        TopicService topicService = new TopicService();
        JsonResult jsonResult = new JsonResult();

        try {
            topicService.addNodeByNodeName(nodeName);
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (Exception e){
            jsonResult.setState(JsonResult.ERROR);
            e.printStackTrace();
        }
        renderJSON(jsonResult,resp);
    }
}
