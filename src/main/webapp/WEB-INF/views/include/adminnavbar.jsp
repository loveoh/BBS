<%--
  Created by IntelliJ IDEA.
  User: loveoh
  Date: 2016/12/28
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse  navbar-static-top">
    <div class="navbar-inner">
        <a class="brand" href="/adminhome">BBS 管理系统</a>
        <ul class="nav">
            <li class="active"><a href="#">首页</a></li>
            <li><a href="/admin/topic">主题管理</a></li>
            <li><a href="/admin/node">节点管理</a></li>
            <li><a href="/admin/user">用户管理</a></li>
        </ul>
        <ul class="nav pull-right">
            <li><a href="/admin/logout">安全退出</a></li>
        </ul>
    </div>
</div>