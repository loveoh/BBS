package com.kaishengit.dao;

import com.kaishengit.entity.LoginLog;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by loveoh on 2016/12/16.
 */
public class LoginLogDao {
    public void saveLog(LoginLog loginLog) {

        String sql = "insert into t_login_log(ip,userid)values(?,?)";
        DbHelp.update(sql,loginLog.getIp(),loginLog.getUserid());
    }

    public LoginLog findByUserId(Integer userid) {
        String sql ="select * from t_login_log where userid = ? order by logintime desc limit 0,1";
        return DbHelp.query(sql,new BeanHandler<LoginLog>(LoginLog.class),userid);
    }
}
