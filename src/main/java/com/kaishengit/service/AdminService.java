package com.kaishengit.service;

import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.Page;
import com.kaishengit.vo.TopicVo;
import com.kaishengit.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loveoh on 2016/12/28.
 */
public class AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminService.class);
    private AdminDao adminDao = new AdminDao();
    private ReplyDao replyDao = new ReplyDao();
    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();
    private UserDao userDao = new UserDao();
    private LoginLogDao logDao = new LoginLogDao();

    public Admin login(String adminname, String password, String ip) {
        Admin admin = adminDao.findByAdminname(adminname);
        if (admin != null) {
            if (DigestUtils.md5Hex(Config.get("user_password_salt") + password).equals(admin.getPassword())) {
                logger.info("管理员{}登录了,IP是{}", adminname, ip);
                return admin;
            } else {
                throw new ServiceException("账号或密码错误");
            }
        } else {
            throw new ServiceException("该账户不存在");
        }
    }

    /**
     * 根据帖子的id删除帖子
     *
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
        } else {
            throw new ServiceException("该帖子不存在或已被删除");
        }
    }

    /**
     * 查找用户管理页面的信息(此处定义了一个userVo,用于存储该页面的数据)
     *
     * @param p 分页中的页码数
     * @return 某一个页码中的数据
     */
    public Page<UserVo> findUserVoList(String p) {
        int count = userDao.findUserList().size();
        if (StringUtils.isNotEmpty(p) && StringUtils.isNumeric(p)) {
            Page<UserVo> page = new Page<>(count, Integer.valueOf(p));
            int pageStart = page.getStart();
            int pageSize = page.getPageSize();

            List<UserVo> userVoList = userDao.findUserVoList(pageStart, pageSize);
            page.setItems(userVoList);
            return page;
        } else {
            throw new ServiceException("参数异常");
        }
    }

    /**
     * 根据userid查找user
     * @param userid
     * @return
     */
    public User findUserByUserId(String userid) {
        User user = userDao.findById(Integer.valueOf(userid));
        if (user != null){
            return user;
        }else {
            throw new ServiceException("该账号不存在或已经被删除");
        }
    }

    public void update(User user) {
        userDao.update(user);
    }

    public Topic findTopicByTopicId(String topicid) {
        return topicDao.findByTopicId(Integer.valueOf(topicid));
    }



    public void updateTopic(Topic topic) {
        topicDao.update(topic);
    }

    public Node findNodeBynodeId(String nodeid) {
        return nodeDao.findById(Integer.valueOf(nodeid));
    }

    /**
     * 查询首页的数据,此处定义了topicVo对象,封装了首页的所需的数据
     * @param pageNo
     * @return
     */
    public Page<TopicVo> findTopicVo(Integer pageNo) {
        int count = topicDao.topicVoCount();
        Page<TopicVo> page = new Page<>(count,pageNo);
        int pageSize = page.getPageSize();
        int pageStart = page.getStart();
        //分页查找主页面中的数据
        List<TopicVo> topicVoList = topicDao.findTopicVoList(pageStart,pageSize);
        page.setItems(topicVoList);

        return page;
    }
}
/**
 * 查找用户管理页面的信息(此处定义了一个userVo,用于存储该页面的数据)
 *
 * @return
 *//*
    public List<UserVo> findUserVoList(String p) {
        List<User> userList = userDao.findUserList();
        List<UserVo> userVoList = new ArrayList<>();
        for (User user :userList){
            UserVo userVo = new UserVo();
            LoginLog loginLog = logDao.findByUserId(user.getId());
            userVo.setUsername(user.getUsername());
            userVo.setCreatetime(user.getCreatetime());
            userVo.setLastlogintime(loginLog.getLogintime());
            userVo.setUserIp(loginLog.getIp());
            userVoList.add(userVo);
        }
        if (StringUtils.isNotEmpty(p) && StringUtils.isNumeric(p)){
            int count = Integer.valueOf(userVoList.size());
            Page<UserVo> page = new Page<>(count,Integer.valueOf(p));

        }

        return userVoList;
    }*/

