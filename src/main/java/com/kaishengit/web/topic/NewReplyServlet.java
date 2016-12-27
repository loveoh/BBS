package com.kaishengit.web.topic;

import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by loveoh on 2016/12/21.
 */
@WebServlet("/newReply")
public class NewReplyServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        String topicid = req.getParameter("topicid");
        User user = getCurrentUser(req);


        TopicService topicService = new TopicService();
        if (StringUtils.isNumeric(topicid)){
            try {
                topicService.addTopicReply(content, Integer.valueOf(topicid), user);
            }catch (ServiceException e){
                resp.sendError(404,e.getMessage());
            }
        }else {
            resp.sendError(404);
        }
        resp.sendRedirect("/topicDetail?topicid="+topicid);

    }
}
