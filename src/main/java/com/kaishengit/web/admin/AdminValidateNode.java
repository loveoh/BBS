package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.jar.JarEntry;

/**
 * Created by loveoh on 2016/12/28.
 */
@WebServlet("/admin/validatenode")
public class AdminValidateNode extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeName = req.getParameter("nodename");
        //转码,解决中文问题
        nodeName = StringUtils.iosChangeToUtf8(nodeName);
        TopicService topicService = new TopicService();
        Node node = topicService.findNodeByNodeName(nodeName);
        if (node == null){
           renderText("true",resp);
        }else {
           renderText("false",resp);
        }
    }
}
