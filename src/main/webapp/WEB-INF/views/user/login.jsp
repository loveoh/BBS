<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/sweetalert/sweetalert.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 登录</span>
        </div>
        <c:if test="${not empty requestScope.message}">
            <div class="alert alert-success">
                    ${requestScope.message}
            </div>
        </c:if>
       <%-- 判断是直接在登录页面中登录,还是在其他页面中登录--%>
        <c:if test="${not empty param.redirect}">
            <div class="alert alert-danger">
                   请登录后操作
            </div>
        </c:if>
        <form action="" class="form-horizontal" id="loginForm">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="username">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label"></label>
                <div class="controls">
                    <a href="/foundpassword">忘记密码</a>
                </div>
            </div>

            <div class="form-actions">
                <button type="button" id="loginBtn" class="btn btn-primary">登录</button>

                <a class="pull-right" href="/regist">注册账号</a>
            </div>
        </form>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetalert/sweetalert.min.js"></script>
<script src="/static/js/user/login.js"></script>
</body>
</html>