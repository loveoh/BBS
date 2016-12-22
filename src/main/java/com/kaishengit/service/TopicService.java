package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.ReplyDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by loveoh on 2016/12/20.
 */
public class TopicService {
    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();
    private UserDao userDao = new UserDao();
    private ReplyDao replyDao = new ReplyDao();
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    /**
     * 查询发帖节点的集合
     * @return
     */
    public List<Node> findAllNode() {

        return nodeDao.findAllNode();
    }

    /**
     * 将新发的帖子存入topic表中
     * @param title
     * @param content
     * @param nodeid
     * @param userid
     * @return
     */
    public Topic addNewTopic(String title, String content,  Integer nodeid,Integer userid) {
        //将属性封装成对象
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setUserid(userid);
        topic.setNodeid(nodeid);

        Integer topicid = topicDao.save(topic);
        topic.setId(topicid);

        //发帖时修改一下node表中的topicnum +1
        Node node = nodeDao.findById(nodeid);
        node.setTopicnum(node.getTopicnum()+1);
        nodeDao.update(node);
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
                //每一次请求都添加一次帖子的点击数量 clikcnum
                topic.setClicknum(topic.getClicknum()+1);
                topicDao.update(topic);
                logger.info("{}发布了主题为{}的帖子",user.getUsername(),topic.getTitle());
                return topic;

            }else {
                throw new ServiceException("帖子不存在或已经被删除");
            }
        }else {
            throw new ServiceException("参数错误");
        }

    }

    /**
     * 存储用户的回复
     * @param content 回复内容
     * @param topicid 回复哪个帖子
     * @param userid 哪个用户的回帖
     */
    public void addTopicReply(String content, Integer topicid, Integer userid) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setTopicid(topicid);
        reply.setUserid(userid);

        replyDao.saveReply(reply);
        //回复帖子,需要在topic表中改写replynum和lastreplytime的值
        Topic topic = topicDao.findByTopicId(topicid.toString());
        if (topic != null){
            topic.setReplynum(topic.getReplynum()+1);
            topic.setLastreplytime(new Timestamp(new Date().getTime()));
            topicDao.update(topic);
        }else{
            throw new ServiceException("帖子不存在或者已被删除");
        }

    }


    /**
     * 根据帖子的ID 查询该帖子的回复信息
     * @param topicid
     * @return
     */
    public List<Reply> findAllReply(String topicid) {

        return replyDao.findAllReply(Integer.valueOf(topicid));
    }
}
