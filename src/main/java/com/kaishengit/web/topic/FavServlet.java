package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by loveoh on 2016/12/24.
 */
@WebServlet("/topicFav")
public class FavServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String action = req.getParameter("action");
        User user = getCurrentUser(req);
        TopicService topicService = new TopicService();
        JsonResult jsonResult = new JsonResult();
        if (action != null && StringUtils.isNumeric(topicid)) {
            if ("fav".equals(action)) {
                topicService.favTopic(Integer.valueOf(topicid), user.getId());
                jsonResult.setState(jsonResult.SUCCESS);
            } else if ("unfav".equals(action)) {
                topicService.unfavTopic(Integer.valueOf(topicid), user.getId());
                jsonResult.setState(jsonResult.SUCCESS);
            }
            //收藏或者取消收藏之后,我都要查一遍数据库,将准确数据回传到页面中去,保证数据的准确性
            Topic topic = topicService.findByTopciId(topicid);
            int favNum = topic.getFavnum();
            jsonResult.setData(favNum);
        } else {
            jsonResult.setMessage("参数异常");
        }
        renderJSON(jsonResult, resp);
    }


}

