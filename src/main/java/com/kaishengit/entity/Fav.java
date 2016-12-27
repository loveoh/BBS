package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by loveoh on 2016/12/23.
 */
public class Fav {
    private Integer topicid;
    private  Integer userid;
    private Timestamp createtime;

    public Integer getTopicid() {
        return topicid;
    }

    public void setTopicid(Integer topicid) {
        this.topicid = topicid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
