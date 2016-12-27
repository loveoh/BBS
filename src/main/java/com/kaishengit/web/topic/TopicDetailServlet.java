package com.kaishengit.web.topic;

import com.kaishengit.entity.Fav;
import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
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
import java.util.List;

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
            //查询reply表,将内容信息显示到jsp中,获得topicid对应的回复列表
            List<Reply> replyList = topicService.findAllReply(topicid);

            req.setAttribute("topic",topic);
            req.setAttribute("replyList",replyList);
            User user = getCurrentUser(req);
            //查询该用户是否已经收藏过该帖子
            if(user != null && StringUtils.isNumeric(topicid)) {
                Fav fav = topicService.findFav(topicid, user);
                req.setAttribute("fav",fav);
            }
            forward("topic/topicdetail",req,resp);
        }catch (ServiceException ex){
            resp.sendError(404);
        }

    }
}
