
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/sweetalert/sweetalert.css">
</head>
<body>
<%@include file="../include/adminnavbar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${page.items}" var="userVo">


        <tbody>
        <tr>
            <td>${userVo.username}</td>
            <td>${userVo.createtime}</td>
            <td>${userVo.logintime}</td>
            <td>${userVo.ip}</td>
            <c:if test="${userVo.status == 1}">
                <td>
                    <a href="javascript:;" class="update" rel="${userVo.userid}">禁用</a>
                </td>
            </c:if>

            <c:if test="${userVo.status == 2}">
                <td>
                    <a href="javascript:;" class="update" rel="${userVo.userid}">恢复</a>
                </td>
            </c:if>

        </tr>
        </tbody>
        </c:forEach>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/sweetalert/sweetalert.min.js"></script>
<script>
    $(function(){
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages:${page.pageSize},
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?page={{number}}"
        });

        $(".update").click(function () {
            var id = $(this).attr("rel");
                        $.ajax({
                            url:"/admin/user",
                            type:"post",
                            data:{"id":id},
                            success:function (json) {
                                if(json.state == "success"){
                                    swal("success");
                                    if (json.data == 1){
                                        $(this).text("禁用");
                                        window.history.go(0);
                                    }else if(json.data == 2) {
                                        $(this).text("恢复");
                                        window.history.go(0);
                                    }

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

</script>
</body>
</html>
