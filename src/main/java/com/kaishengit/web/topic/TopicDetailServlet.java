package com.kaishengit.web.topic;

import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by loveoh on 2016/12/20.
 */
@WebServlet("/topicDetail")
public class TopicDetailServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        TopicService topicService = new TopicService();

        try {
            Topic topic = topicService.findByTopciId(topicid);
            req.setAttribute("topic",topic);

            forward("topic/topicdetail",req,resp);
        }catch (ServiceException ex){
            resp.sendError(404);
        }

    }
}
