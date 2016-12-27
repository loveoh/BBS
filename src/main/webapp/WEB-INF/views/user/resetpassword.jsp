<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 找回密码</span>

        <form action="" class="form-horizontal" id="resetForm">
            <input type="hidden" name="id" value="${requestScope.id}">
            <input type="hidden" name="token" value="${requestScope.token}">
            <div class="control-group">
                <label class="control-label">新密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">确认密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>


            <div class="form-actions">
                <button type="button" id="resetBtn" class="btn btn-primary">重置</button>


            </div>

        </form>



    </div>
    <!--box end-->
</div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/resetpassword.js"></script>
</body>
</html>