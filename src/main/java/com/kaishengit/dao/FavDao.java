package com.kaishengit.dao;

import com.kaishengit.entity.Fav;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by loveoh on 2016/12/23.
 */
public class FavDao {
    public Fav findFav(Integer topicid, Integer userid) {
        String sql ="select * from t_fav where topicid = ? and userid =?";
        return DbHelp.query(sql,new BeanHandler<Fav>(Fav.class),topicid,userid);
    }

    public void add(Fav fav) {
        String sql = "insert into t_fav(userid,topicid)values(?,?)";
        DbHelp.update(sql,fav.getUserid(),fav.getTopicid());
    }

    public void delete(Fav fav) {
        String sql = "delete from t_fav where userid=? and topicid= ?";
        DbHelp.update(sql,fav.getUserid(),fav.getTopicid());

    }
}
