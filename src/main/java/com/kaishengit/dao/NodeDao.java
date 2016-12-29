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
        return DbHelp.query(sql,new BeanListHandler<>(Node.class));
    }

    public Node findById(Integer nodeid) {
        String sql = "select * from t_node where id = ?";

        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodeid);
    }

    public void update(Node node) {
        String sql = "update t_node set nodename=?,topicnum=? where id = ?";
        DbHelp.update(sql,node.getNodename(),node.getTopicnum(),node.getId());
    }


    public void deleteNodeByNodeId(String nodeid) {
        String sql = "delete from t_node where id = ?";
        DbHelp.update(sql,nodeid);
    }

    public Node findNodeByNodeName(String nodeName) {
        String sql = "select * from t_node where nodename =?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodeName);
    }

    public void addNode(String nodeName) {
        String sql = "insert into t_node(nodename) values(?)";
        DbHelp.update(sql,nodeName);
    }
}
