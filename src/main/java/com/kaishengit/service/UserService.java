package com.kaishengit.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaishengit.dao.LoginLogDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.LoginLog;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.utils.Config;
import com.kaishengit.utils.EmailUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by loveoh on 2016/12/15.
 */
public class UserService {
    private UserDao userDao = new UserDao();
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private LoginLogDao logDao = new LoginLogDao();
    private String salt = Config.get("user_password_salt");

    /**
     * 发送激活邮件缓存的token
     */
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();
    /**
     * 找回密码验证邮件,30分钟有效
     */
    private static Cache<String, String> passwordCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();
    /**
     * 防止用户过快操作的缓存
     */
    private static Cache<String, String> activeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    /**
     * 校验用户是否存在
     *
     * @param username
     * @return
     */
    public Boolean validateUsername(String username) {
        String name = Config.get("no.signup.usernames");
        List<String> list = Arrays.asList(name.split(","));
        if (list.contains(username)) {
            return false;
        }
        return userDao.findByName(username) == null;
    }

    /**
     * 校验电子邮箱是否存在
     *
     * @param email
     * @return
     */
    public User findByEmail(String email) {

        return userDao.findByEmail(email);
    }

    /**
     * 注册方法,并且发送用户激活邮件
     *
     * @param username
     * @param password
     * @param email
     * @param phone
     */
    public void add(String username, String password, String email, String phone) {
        User user = new User();
        user.setUsername(username);
        //将密码在服务端进行md5加密
        user.setPassword(DigestUtils.md5Hex(Config.get("user_password_salt") + password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatar(User.DEFAULT_USER_AVATAR);

        userDao.add(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid = UUID.randomUUID().toString();
                String url = "http://localhost/user/active?_=" + uuid;

                //放入缓存6小时,表示该邮件6小时内可以被激活
                cache.put(uuid, username);
                String html = "<h3>Dear:" + username + ":</h3>请点击<a href=" + url + " >链接</a><br><h3>yc_bbs</h3>";
                EmailUtils.sendHtmlEmail(email, "yc_bbs激活邮件", html);
            }
        });
        thread.start();
    }

    /**
     * 根据缓存找到对应的账号,并激活账号
     *
     * @param token
     */
    public void activeUser(String token) {
        //根据缓存查找相对应的账号
        String username = cache.getIfPresent(token);
        if (username == null) {
            throw new ServiceException("激活失败,邮件已过期");
        } else {
            User user = userDao.findByName(username);
            if (user == null) {
                throw new ServiceException("找不到账号");
            } else {
                user.setStatus(User.USER_ACTIVE);
                userDao.update(user);
                //删除缓存中的数据
                cache.invalidate(token);
            }

        }
    }


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public User findByNameAndPassword(String username, String password, String ip) {
        User user = userDao.findByName(username);
        if (user != null && user.getPassword().equals(DigestUtils.md5Hex(Config.get("user_password_salt") + password))) {
            if (user.getStatus().equals(User.USER_ACTIVE)) {
                //填写登录log
                LoginLog loginLog = new LoginLog();
                loginLog.setIp(ip);
                loginLog.setUserid(user.getId());

                logDao.saveLog(loginLog);
                //填写系统log
                logger.info("{}登陆了系统", ip);

                return user;

            } else if (user.getStatus().equals(User.USER_UNACTIVE)) {
                throw new ServiceException("该账号未激活");
            } else {
                throw new ServiceException("该账号已被禁用");
            }

        } else {
            throw new ServiceException("账号或密码错误");
        }
    }

    /**
     * 发送邮件找回密码
     *
     * @param sessionId 根据sessionID来确定客户端,防止用户过快操作
     * @param type
     * @param value
     */
    public void findBackPassword(String sessionId, String type, String value) {
        if (activeCache.getIfPresent(sessionId) == null) {
            if ("phone".equals(type)) {
                // TODO: 2016/12/17
            } else {
                User user = userDao.findByEmail(value);
                if (user != null) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String uuid = UUID.randomUUID().toString();
                            String url = "http://localhost/user/resetpassword?token=" + uuid;

                            //将用户账号放入到缓存中,以确定是哪一个账号修改密码
                            passwordCache.put(uuid, user.getUsername());
                            System.out.println("用户名:" + user.getUsername());
                            String html = "<h3>Dear:" + user.getUsername() + "</h3><br>请点击以下<a href=" + url + ">链接</a>找回密码,该邮件30分钟内有效";
                            EmailUtils.sendHtmlEmail(value, "密码找回", html);
                        }
                    });
                    thread.start();
                    //将该客户端的sessionID加入到缓存中,限制客户端频繁操作
                    activeCache.put(sessionId, "xxxxxx");
                } else {
                    throw new ServiceException("该邮箱不存在");
                }
            }
        } else {
            throw new ServiceException("操作频率过快,请稍后再试");
        }
    }

    /**
     * 重置密码
     *
     * @param password
     */
    public void resetPassword(String password, Integer id, String token) {
        String username = passwordCache.getIfPresent(token);
        if (username != null) {
            User user = userDao.findById(id);
            if (user != null) {
                password = DigestUtils.md5Hex(Config.get("user_password_salt") + password);
                user.setPassword(password);
                userDao.update(user);
                logger.info("{}使用了找回密码功能", username);

            } else {
                throw new ServiceException("用户不存在");
            }
        } else {
            throw new ServiceException("邮件已过期");
        }
    }

    /**
     * 根据token查找用户信息
     *
     * @param token
     * @return
     */
    public User findByToken(String token) {
        String username = passwordCache.getIfPresent(token);
        if (StringUtils.isEmpty(username)) {
            throw new ServiceException("邮件已失效");
        } else {
            User user = userDao.findByName(username);
            if (StringUtils.isEmpty(username)) {
                throw new ServiceException("账户不存在");
            } else {
                return user;
            }
        }
    }


    /**
     * 修改电子邮件
     *
     * @param user  要修改的账户
     * @param email 新的电子邮件
     */
    public void updateEmail(User user, String email) {
        user.setEmail(email);
        userDao.update(user);
    }

    /**
     * 修改用户密码
     *
     * @param user        要修改的账户
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     */
    public void updatePassword(User user, String oldPassword, String newPassword) {
        if (user != null && user.getPassword().equals(DigestUtils.md5Hex(salt + oldPassword))) {
            user.setPassword(DigestUtils.md5Hex(salt + newPassword));
            userDao.update(user);
        } else {
            throw new ServiceException("原始密码不正确,请重新输入");
        }
    }

    /**
     * 更改用户头像
     *
     * @param user
     * @param avatar
     */
    public void uapdateAvatar(User user, String avatar) {
        user.setAvatar(avatar);
        userDao.update(user);
    }
}



