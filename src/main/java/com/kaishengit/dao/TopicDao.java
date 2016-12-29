package com.kaishengit.dao;


import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.DbHelp;
import com.kaishengit.utils.StringUtils;
import com.kaishengit.vo.TopicVo;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by loveoh on 2016/12/20.
 */
public class TopicDao {


    public Integer save(Topic topic) {
        String sql = "insert into t_topic(title,content,userid,nodeid,lastreplytime)values(?,?,?,?,?)";

        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getUserid(),topic.getNodeid(),topic.getLastreplytime());
    }

    public static Topic findByTopicId(Integer topicid) {
        String sql = "select * from t_topic where id = ?";
        return DbHelp.query(sql,new BeanHandler<Topic>(Topic.class),topicid);
    }


    public void update(Topic topic) {
        String sql = "update t_topic set title=?,content=?,clicknum=?,favnum=?,thankyounum=?,replynum=?,lastreplytime=?,userid=?,nodeid=? where id = ?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClicknum(),topic.getFavnum(),topic.getThankyounum(),topic.getReplynum(),topic.getLastreplytime(),topic.getUserid(),topic.getNodeid(),topic.getId());
    }

    public int count() {
        String sql = "select count(*) from t_topic";
         return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public int count(String nodeid) {
        String sql = "select count(*) from t_topic where nodeid = ?";

        return DbHelp.query(sql,new ScalarHandler<Long>(),nodeid).intValue();
    }

    public List<Topic> findList(Map<String, Object> map) {
        String sql = "SELECT tu.id, tu.username,tu.avatar, tt.* FROM t_topic tt, t_user tu WHERE tt.userid = tu.id ";
        String nodeid = map.get("nodeid") == null?null:String.valueOf(map.get("nodeid"));
        String where = "";

        List<Object> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(nodeid)){
            where = "and nodeid = ? ";
            list.add(nodeid);
        }
        where+= "ORDER BY tt.lastreplytime DESC LIMIT ?,?";
        sql+= where;

        list.add(map.get("startpage"));
        list.add(map.get("pagesize"));

        //多表联查的时候就需要自己写具体的实现类
        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet rs) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(rs,Topic.class);
                User user = new User();
                user.setId(rs.getInt("userid"));
                user.setAvatar(Config.get("qiniu.domain") + rs.getString("avatar"));
                user.setUsername(rs.getString("username"));
                topic.setUser(user);

                return topic;
            }
        },list.toArray());
    }


    /**
     * 根据帖子id删除帖子
     * @param topicid
     */
    public void deleteTopic(String topicid) {
        String sql = "delete from t_topic where id = ?";
        DbHelp.update(sql,topicid);

    }


    public int topicVoCount() {
        String sql = "SELECT COUNT(*) FROM(SELECT COUNT(*) FROM t_topic GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d')) AS topicCount";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();

    }

    public List<TopicVo> findTopicVoList(int pageStart, int pageSize) {
        String sql = "SELECT COUNT(*) AS newtopicnum,DATE_FORMAT(createtime,'%Y-%m-%d') AS DATE,\n" +
                "(SELECT COUNT(*)FROM t_reply WHERE DATE_FORMAT(createtime,'%Y-%m-%d') = (DATE_FORMAT(t_topic.createtime,'%Y-%m-%d'))) AS newreplynum \n" +
                "FROM t_topic  GROUP BY (DATE_FORMAT(createtime,'%Y-%m-%d')) \n" +
                "ORDER BY (DATE_FORMAT(createtime,'%Y-%m-%d')) DESC LIMIT ?,?";
        return DbHelp.query(sql,new BeanListHandler<TopicVo>(TopicVo.class),pageStart,pageSize);
    }
}
