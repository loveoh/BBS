package com.kaishengit.dao;

import com.kaishengit.entity.Reply;
import com.kaishengit.entity.User;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by loveoh on 2016/12/21.
 */
public class ReplyDao {

    public void saveReply(Reply reply) {
        String sql = "insert into t_reply(content,topicid,userid)values(?,?,?)";
        DbHelp.update(sql,reply.getContent(),reply.getTopicid(),reply.getUserid());
    }

    public List<Reply> findAllReply(Integer topicid) {
        String sql ="SELECT tu.id,tu.username,tu.avatar ,tr.* FROM t_reply tr ,t_user tu WHERE tr.userid = tu.id AND topicid = ?";

        return DbHelp.query(sql, new AbstractListHandler<Reply>() {
            @Override
            protected Reply handleRow(ResultSet rs) throws SQLException {
                Reply reply = new BasicRowProcessor().toBean(rs,Reply.class);
                User user = new User();
                user.setAvatar(Config.get("qiniu.domain")+rs.getString("avatar"));
                user.setUsername(rs.getString("username"));
                user.setId(rs.getInt("userid"));
                reply.setUser(user);

                return reply;
            }
        },topicid);
    }
}
