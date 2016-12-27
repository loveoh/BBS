package com.kaishengit.service;

import com.google.common.collect.Maps;
import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by loveoh on 2016/12/20.
 */
public class TopicService {
    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();
    private UserDao userDao = new UserDao();
    private ReplyDao replyDao = new ReplyDao();
    private FavDao favDao = new FavDao();
    private NotifyDao notifyDao = new NotifyDao();
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
        //新帖的默认回复时间暂时设置为当前时间
        topic.setLastreplytime(new Timestamp(new DateTime().getMillis()));

        //存储topic之后,将topicid返回到前段页面中去,然后前端页面进行跳转
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
            Topic topic = TopicDao.findByTopicId(Integer.valueOf(topicid));
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
     * @param user 哪个用户的回帖
     */
    public void addTopicReply(String content, Integer topicid, User user) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setTopicid(topicid);
        reply.setUserid(user.getId());

        replyDao.saveReply(reply);
        //回复帖子,需要在topic表中改写replynum和lastreplytime的值
        Topic topic = topicDao.findByTopicId(topicid);
        if (topic != null) {
            topic.setReplynum(topic.getReplynum() + 1);
            topic.setLastreplytime(new Timestamp(new Date().getTime()));
            topicDao.update(topic);
        } else {
            throw new ServiceException("帖子不存在或者已被删除");
        }

        //新增回复通知
        if (!user.getId().equals(topic.getUserid())) {
            Notify notify = new Notify();
            //notify.setContent("\"您的主题 <a href=\\\"/topicDetail?topicid=\"+topic.getId()+\"\\\">[\"+ topic.getTitle()+\"] </a> 有了新的回复,请查看.\"");
            notify.setContent("您的主题 <a href=\"/topicDetail?topicid="+topic.getId()+"\">" + topic.getTitle() + "</a>有了新回复,请查看");
            notify.setUserid(topic.getUserid());
            notify.setState(Notify.NOTIFY_STATE_UNREAD);

            notifyDao.save(notify);



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

    /**
     * 修改帖子
     * @param title
     * @param content
     * @param nodeid
     * @param topicid
     */
    public void editTopic(String title, String content, String nodeid, String topicid) {
        Topic topic = topicDao.findByTopicId(Integer.valueOf(topicid));
        if (topic != null) {
            topic.setTitle(title);
            topic.setContent(content);

            if(topic.getNodeid() == Integer.valueOf(nodeid)){
                topicDao.update(topic);
            }else{


                //node表中原来的nodenum -1
                Node node1 = nodeDao.findById(topic.getNodeid());
                node1.setTopicnum(node1.getTopicnum() - 1);
                nodeDao.update(node1);
                //新修改的帖子的节点,将nodenum + 1
                Node node2 = nodeDao.findById(Integer.valueOf(nodeid));
                node2.setTopicnum(node2.getTopicnum() + 1);
                nodeDao.update(node2);

                topic.setNodeid(Integer.valueOf(nodeid));
                topicDao.update(topic);
            }

        }else{
            throw new ServiceException("帖子被不存在或被删除");
        }

    }

    /**
     * 查询关系表中,看改用户是否收藏了该帖子
     * @param topicid
     * @param user
     * @return
     */
    public  Fav findFav(String topicid, User user) {

        return favDao.findFav(Integer.valueOf(topicid),user.getId());
    }

    /**
     * 收藏帖子
     * @param topicid
     * @param userid
     */
    public void favTopic(Integer topicid, Integer userid) {
        Fav fav = new Fav();
        fav.setUserid(userid);
        fav.setTopicid(topicid);
        favDao.add(fav);

        //收藏数要+1
        Topic topic = topicDao.findByTopicId(topicid);
        topic.setFavnum(topic.getFavnum() +1);
        topicDao.update(topic);
    }

    /**
     * 取消收藏
     * @param topicid
     * @param userid
     */
    public void unfavTopic(Integer topicid, Integer userid) {
        Fav fav = new Fav();
        fav.setUserid(userid);
        fav.setTopicid(topicid);
        favDao.delete(fav);

        //收藏数-1
        Topic topic = topicDao.findByTopicId(topicid);
        topic.setFavnum(topic.getFavnum() - 1);
        topicDao.update(topic);



    }

    /**
     *  查询当前页的数据数量
     * @param nodeid
     * @param pageNo
     * @return
     */
    public Page<Topic> findAllTopics(String nodeid, Integer pageNo) {
        Map<String,Object> map = Maps.newHashMap();
        //表中的数据的总量
        int count  = 0;
        if (StringUtils.isEmpty(nodeid)){
            count = topicDao.count();
        }else {
            //nodeid节点中的所有数据量0
            count = topicDao.count(nodeid);
        }

        Page<Topic> topicPage = new Page<>(count,pageNo);
        map.put("nodeid",nodeid);
        map.put("startpage",topicPage.getStart());
        map.put("pagesize",topicPage.getPageSize());

        //获取具体某一页的所有帖子
        List<Topic> topicList = topicDao.findList(map);
        topicPage.setItems(topicList);

        return topicPage;

    }
}
