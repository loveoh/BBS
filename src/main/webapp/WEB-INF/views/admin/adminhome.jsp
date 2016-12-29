
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminnavbar.jsp"%>
<!--header-bar end-->


<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>日期</th>
            <th>新主题数</th>
            <th>新回复数</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                2016-12-28
            </td>
            <td>123</td>
            <td>2546</td>

            <td>
                <a href="">详情</a>
            </td>
        </tr>
        <tr>
            <td>
                2016-12-27
            </td>
            <td>123</td>
            <td>2546</td>

            <td>
                <a href="">详情</a>
            </td>
        </tr>
        <tr>
            <td>
                2016-12-26
            </td>
            <td>123</td>
            <td>2546</td>

            <td>
                <a href="">详情</a>
            </td>
        </tr>
        <tr>
            <td>
                2016-12-25
            </td>
            <td>123</td>
            <td>2546</td>

            <td>
                <a href="">详情</a>
            </td>
        </tr><tr>
            <td>
                2016-12-24
            </td>
            <td>123</td>
            <td>2546</td>

            <td>
                <a href="">详情</a>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function(){
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?nodeid=${param.nodeid}&page={{number}}"
        });
    });
</script>

</body>
</html>
