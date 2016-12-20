package com.kaishengit.dao;

import com.kaishengit.entity.LoginLog;
import com.kaishengit.utils.DbHelp;

/**
 * Created by loveoh on 2016/12/16.
 */
public class LoginLogDao {
    public void saveLog(LoginLog loginLog) {

        String sql = "insert into t_login_log(ip,userid)values(?,?)";
        DbHelp.update(sql,loginLog.getIp(),loginLog.getUserid());
    }
}
