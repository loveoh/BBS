package com.kaishengit.web;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by loveoh on 2016/12/17.
 */
@WebServlet("/home")
public class HomeServlet extends  BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从url中获得参数
        String nodeid = req.getParameter("nodeid");
        String p = req.getParameter("page");
        //判断页码是否为数字,如果不是数字,直接返回首页
        Integer pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;

        if (!StringUtils.isNumeric(nodeid) && StringUtils.isNotEmpty(nodeid)){
            forward("index",req,resp);
            //两个forward;防止出现两次跳转
            return;
        }
        TopicService topicService = new TopicService();
        //获取nodeList列表,显示到前端页面
        List<Node> nodeList = topicService.findAllNode();

        //分页查找中,其中一页的内容
        Page<Topic> page = topicService.findAllTopics(nodeid,pageNo);
        req.setAttribute("page",page);
        req.setAttribute("nodeList",nodeList);

        forward("index",req,resp);
    }
}
