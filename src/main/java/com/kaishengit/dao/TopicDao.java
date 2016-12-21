package com.kaishengit.dao;


import com.kaishengit.entity.Topic;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by loveoh on 2016/12/20.
 */
public class TopicDao {


    public Integer save(Topic topic) {
        String sql = "insert into t_topic(title,content,userid,nodeid)values(?,?,?,?)";

        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getUserid(),topic.getNodeid());
    }

    public static Topic findByTopicId(String topicid) {
        String sql = "select * from t_topic where id = ?";
        return DbHelp.query(sql,new BeanHandler<Topic>(Topic.class),topicid);
    }
}
