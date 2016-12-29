package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.ReplyDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by loveoh on 2016/12/28.
 */
public class AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminService.class);
    private AdminDao adminDao = new AdminDao();
    private ReplyDao replyDao = new ReplyDao();
    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();


    public Admin login(String adminname, String password,String ip) {
        Admin admin = adminDao.findByAdminname(adminname);
        if (admin != null){
            if (DigestUtils.md5Hex(Config.get("user_password_salt") + password).equals(admin.getPassword())){
                logger.info("管理员{}登录了,IP是{}",adminname,ip);
                return admin;
            }else {
                throw new ServiceException("账号或密码错误");
            }
        }else {
            throw new ServiceException("该账户不存在");
        }
    }

    /**
     * 根据帖子的id删除帖子
     * @param topicid 要删除的帖子的id
     */
    public void deleteById(String topicid) {
        //1.先删除帖子的所有回复
        replyDao.deleteAllById(topicid);
        //2.更改节点下帖子的数量
        Topic topic = topicDao.findByTopicId(Integer.valueOf(topicid));
        if (topic != null) {
            //根据topicid获得nodeid
            Node node = nodeDao.findById(topic.getNodeid());
            node.setTopicnum(node.getTopicnum() - 1);
            nodeDao.update(node);

            //3.删除帖子
            topicDao.deleteTopic(topicid);
        }else {
            throw new ServiceException("该帖子不存在或已被删除");
        }
    }
}
