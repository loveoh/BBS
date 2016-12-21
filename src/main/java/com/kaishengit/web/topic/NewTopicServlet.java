package com.kaishengit.web.topic;

import com.kaishengit.dao.TopicDao;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by loveoh on 2016/12/20.
 */
@WebServlet("/newTopic")
public class NewTopicServlet extends BaseServlet{
    TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取所有的节点,并传到jsp中
        List<Node> nodeList = topicService.findAllNode();
        req.setAttribute("nodeList",nodeList);
        forward("topic/newtopic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取nweTopic页面中的数据
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");

        User user = getCurrentUser(req);
      //  Topic topic = topicService.addNewTopic(title,content,user.getId(),Integer.valueOf(nodeid));
        Topic topic = topicService.addNewTopic(title, content, Integer.valueOf(nodeid), user.getId());

        JsonResult jsonResult = new JsonResult(topic);
        renderJSON(jsonResult,resp);
    }
}
