package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.Page;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by loveoh on 2016/12/28.
 */
@WebServlet("/admin/topic")
public class AdminTopicServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("page");
        Integer pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;

        TopicService topicService = new TopicService();
        Page<Topic> page = topicService.findAllTopics("",pageNo);

        List<Node> nodeList = topicService.findAllNode();
        req.setAttribute("nodeList",nodeList);
        req.setAttribute("page",page);
        forward("/admin/admintopic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("del") ){
            delTopic(req,resp);
        }else if ("update".equals(action)){
            updateTopic(req,resp);
        }

    }
    private void updateTopic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("id");
        String nodeid = req.getParameter("nodeid");
        AdminService adminService = new AdminService();
        JsonResult jsonResult = new JsonResult();
        try {
            Topic topic = adminService.findTopicByTopicId(topicid);
            Node node = adminService.findNodeBynodeId(nodeid);
            topic.setNodeid(node.getId());
            //更新节点
            adminService.updateTopic(topic);
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
            throw new ServiceException(e.getMessage());
        }
        renderJSON(jsonResult,resp);
    }




    private void delTopic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        AdminService adminService = new AdminService();
        JsonResult jsonResult = new JsonResult();
        try {
            adminService.deleteById(id);
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
            throw new ServiceException(e.getMessage());
        }
        renderJSON(jsonResult,resp);
    }
}
