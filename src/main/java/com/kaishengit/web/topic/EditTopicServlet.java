package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by loveoh on 2016/12/23.
 */
@WebServlet("/editTopic")
public class EditTopicServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        TopicService topicService = new TopicService();
        //获取七牛的token
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));

        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\":\""+Config.get("qiniu.domain")+"${key}\"}");
        String token = auth.uploadToken(Config.get("qiniu.bucket"),null,3600,stringMap);

        try {
            Topic topic = topicService.findByTopciId(topicid);


            List<Node> nodeList = topicService.findAllNode();
            req.setAttribute("topic",topic);
            req.setAttribute("nodeList",nodeList);
            req.setAttribute("token",token);
            forward("/topic/edittopic",req,resp);
        }catch (ServiceException e){
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");
        String topicid = req.getParameter("topicid");


        JsonResult jsonResult = null;
        if (StringUtils.isNumeric(topicid)){
            TopicService topicService = new TopicService();
            try {
                topicService.editTopic(title, content, nodeid, topicid);
                jsonResult = new JsonResult();
                jsonResult.setState(jsonResult.SUCCESS);
                jsonResult.setData(topicid);

            }catch (ServiceException e){

            }
        }else{
            jsonResult = new JsonResult("参数异常");
        }
        renderJSON(jsonResult,resp);
    }
}
