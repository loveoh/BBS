package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by loveoh on 2016/12/20.
 */
public class TopicService {
    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();
    private UserDao userDao = new UserDao();
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    /**
     * 查询发帖节点的集合
     * @return
     */
    public List<Node> findAllNode() {

        return nodeDao.findAllNode();
    }

    public Topic addNewTopic(String title, String content,  Integer nodeid,Integer userid) {
        //将属性封装成对象
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setUserid(userid);
        topic.setNodeid(nodeid);

        Integer topicid = topicDao.save(topic);
        topic.setId(topicid);
        return  topic;
    }

    /**
     * 通过发帖的ID 查询topic对象
     * @param topicid
     * @return
     */
    public Topic findByTopciId(String topicid) {
        if (StringUtils.isNumeric(topicid)){
            Topic topic = TopicDao.findByTopicId(topicid);
            if (topic != null){
                //通过useid,nodeid分别找到想对应的user和node
                User user = userDao.findById(topic.getUserid());
                Node node = nodeDao.findById(topic.getNodeid());
                //将user,node封装到topic对象中去
                user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);

                logger.info("{}发布了主题为{}的帖子",user.getUsername(),topic.getTitle());
                return topic;

            }else {
                throw new ServiceException("帖子不存在或已经被删除");
            }
        }else {
            throw new ServiceException("参数错误");
        }

    }
}
