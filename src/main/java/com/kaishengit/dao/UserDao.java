package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.utils.DbHelp;
import com.kaishengit.vo.UserVo;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by loveoh on 2016/12/15.
 */
public class UserDao {

    public User findByName(String username) {

        String sql = "select * from t_user where username = ?";

        return DbHelp.query(sql,new BeanHandler<User>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select * from t_user where email = ?";

        return DbHelp.query(sql,new BeanHandler<User>(User.class),email);
    }

    public void add(User user) {
        String sql = "insert into t_user(username,password,email,phone,avatar)values(?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar());
    }

    public void update(User user) {
        String sql = "update t_user set password = ?,email = ?,phone = ?,avatar = ?,status = ? where id = ?";
        DbHelp.update(sql,user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar(),user.getStatus(),user.getId());
    }

    public User findById(Integer id) {
        String sql = "select * from t_user where id = ?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),id);
    }

    public List<User> findUserList() {
        String sql = "select * from t_user";
        return DbHelp.query(sql,new BeanListHandler<User>(User.class));
    }

    public List<UserVo> findUserVoList(int pageStart, int pageSize) {
        String sql = "SELECT MAX(logintime) as logintime,tll.ip,tll.userid, tu.username,tu.createtime,tu.status  FROM t_login_log tll,t_user tu  WHERE tll.userid =tu.id GROUP BY userid LIMIT ?,?";
        return DbHelp.query(sql,new BeanListHandler<UserVo>(UserVo.class),pageStart,pageSize);
    }
}
