package com.kaishengit.dao;

import com.kaishengit.entity.Admin;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by loveoh on 2016/12/28.
 */
public class AdminDao {
    public Admin findByAdminname(String adminname) {
        String sql = "select * from t_admin where adminname = ?";
        return DbHelp.query(sql,new BeanHandler<Admin>(Admin.class),adminname);
    }
}
