<%--
  Created by IntelliJ IDEA.
  User: loveoh
  Date: 2016/12/28
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改节点</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/sweetalert/sweetalert.css">
</head>
<body>
<%@include file="../include/adminnavbar.jsp" %>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <form action="" id="updateNodeForm">
        <legend>修改节点</legend>
        <input type="hidden" value="${nodeid}" name="type">
        <label>节点名称</label>
        <input type="text" name="nodename">
        <div class="form-actions">
            <button type="button" id="updateBtn" class="btn btn-primary">保存</button>
        </div>
    </form>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetalert/sweetalert.min.js"></script>
<script>
    $(function () {


        $("#updateBtn").click(function () {
            $("#updateNodeForm").submit();
        });

        $("#updateNodeForm").validate({
            errorElement: "sapn",
            errorClass: 'text-error',
            rules: {
                nodename:{
                    required:true,
                    remote: "/admin/validatenode"
                }
            },
            messages: {
                nodename:{
                    required:"请填写节点名称",
                    remote: "节点名称已经存在"
                }
            },
            submitHandler: function () {
                $.ajax({
                    url: "/admin/updatenode",
                    type: "post",
                    data: $("#updateNodeForm").serialize(),
                    success: function (json) {
                        if (json.state == "success") {
                            swal("success");
                            window.location.href="/admin/node";
                        } else {
                            swal("修改失败");
                        }
                    },
                    error: function () {
                        swal("服务器忙,请稍后再试");
                    }
                })
            }
        })
    });
</script>
</body>
</html>
