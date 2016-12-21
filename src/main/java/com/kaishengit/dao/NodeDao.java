package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.utils.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by loveoh on 2016/12/20.
 */
public class NodeDao {

    public List<Node> findAllNode() {
        String sql = "select * from t_node";
        return DbHelp.query(sql,new BeanListHandler<Node>(Node.class));
    }

    public Node findById(Integer nodeid) {
        String sql = "select * from t_node where id = ?";

        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodeid);
    }
}