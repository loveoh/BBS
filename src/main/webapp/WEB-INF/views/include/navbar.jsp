<%--
  Created by IntelliJ IDEA.
  User: loveoh
  Date: 2016/12/15
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header-bar">
    <div class="container">
        <a href="/home" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>

        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.curr_user}">
                   <%-- 定义一个标记,登录之后才会触发轮询--%>
                    <span id="isLogin" class="hide">1</span>
                    <li>
                        <a href="/user/setting">
                            <img id="navbar_avatar" src="${sessionScope.curr_user.avatar}?imageView2/1/w/20/h/20"
                                 class="img-circle" alt="">
                            <span>${sessionScope.curr_user.username}</span>
                        </a>
                    </li>
                    <li>
                        <a href="/newTopic"><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <a href="/notify"><i class="fa fa-bell"></i><span class="badge" id="unreadnotify"></span></a>
                    </li>
                    <li>
                        <a href="/user/setting"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="/logout"><i class="fa fa-sign-out"></i></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/login"><i class="fa fa-sign-in"></i></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
