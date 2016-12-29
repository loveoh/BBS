<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: loveoh
  Date: 2016/12/28
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>BBS管理系统</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/sweetalert/sweetalert.css">
    <style>
        .mt20 {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%@include file="../include/adminnavbar.jsp"%>
<!--header-bar end-->
<div class="container-fluid mt20">
    <a href="/admin/newnode" class="btn btn-success">添加新节点</a>
    <table class="table">
        <thead>
        <tr>
            <th>节点名称</th>
            <th>操作</th>
        </tr>
        </thead>
     <c:forEach items="${nodeList}" var="node">
        <tbody>
        <tr>
            <td>${node.nodename}</td>
            <td>
                <a href="/admin/updatenode?nodeid=${node.id}">修改</a>
                <a rel="${node.id}" class="delNode" href="javascript:;">删除</a>
            </td>
        </tr>
        </tbody>
     </c:forEach>
    </table>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/sweetalert/sweetalert.min.js"></script>
<script>
    $(function () {
        $(".delNode").click(function () {
            var id = $(this).attr("rel");
            swal({
                        title: "确定要删除吗?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes, delete it!",
                        closeOnConfirm: false
                    },
                    function(){
                        $.ajax({
                            url:"/admin/node",
                            type:"post",
                            data:{"id":id},
                            success:function (json) {
                                if(json.state == "success"){
                                    swal("Deleted!", "", "success");
                                    window.history.go(0);
                                }else{
                                    swal("删除失败");
                                }
                            },
                            error:function () {
                                swal("服务器忙,请稍后再试");
                            }
                        })

                    });
        });
    });

</script>
</body>
</html>

