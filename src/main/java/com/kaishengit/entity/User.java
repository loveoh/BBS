package com.kaishengit.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by loveoh on 2016/12/15.
 */
public class User implements Serializable{
    /**
     * 用户的默认头像
     */
    public static final String DEFAULT_USER_AVATAR = "u=749833722,3503508912&fm=21&gp=0.jpg";

    /**
     *  用户账号未激活
     */
    public static final Integer USER_UNACTIVE = 0;
    /**
     * 账户已激活
     */
    public static final Integer USER_ACTIVE = 1;
    /**
     * 账户被禁用
     */
    public static final Integer USER_DISABLE = 2;

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private Integer status;
    private Timestamp createtime;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
