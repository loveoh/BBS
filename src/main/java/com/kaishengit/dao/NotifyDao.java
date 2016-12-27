package com.kaishengit.dao;

import com.kaishengit.entity.Notify;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by loveoh on 2016/12/26.
 */
public class NotifyDao {

    public List<Notify> findNotifyListByUserId(Integer userid) {
        String sql = "select * from t_notify where userid = ? order by readtime ,createtime";

        return DbHelp.query(sql,new BeanListHandler<Notify>(Notify.class),userid);
    }



    public void save(Notify notify) {
        String sql = "insert into t_notify(content,state,userid)values(?,?,?)";
        DbHelp.update(sql,notify.getContent(),notify.getState(),notify.getUserid());

    }

    public Notify findById(String id) {
        String sql = "select * from t_notify where id = ?";
        return DbHelp.query(sql,new BeanHandler<Notify>(Notify.class),id);
    }

    public void update(Notify notify) {
        String sql = "update t_notify set content=?,state=?,readtime=?where id=?";
        DbHelp.update(sql,notify.getContent(),notify.getState(),notify.getReadtime(),notify.getId());
    }
}
