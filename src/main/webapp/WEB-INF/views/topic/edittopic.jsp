<%--
  Created by IntelliJ IDEA.
  User: loveoh
  Date: 2016/12/20
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>编辑主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/simditor-emoji.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 编辑主题</span>
        </div>

        <form action="" method="post" style="padding: 20px" id="topicForm">
            <label class="control-label">主题标题</label>
            <input type="hidden" id="topicid" name="topicid" value="${topic.id}">
            <input id="title" name="title" type="text" value="${topic.title}" style="width: 100%;box-sizing: border-box;height: 30px">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor">${topic.content}</textarea>

            <select name="nodeid" id="nodeid" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${requestScope.nodeList}" var="node">
                    <option ${topic.nodeid == node.id ? "selected" :""} value="${node.id}">${node.nodename}</option>
                </c:forEach>

            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button id="sendBtn" class="btn btn-primary">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/simditor-emoji.js"></script>
<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor'),
            //optional options
            toolbar: ['title','bold','italic', 'underline','strikethrough', 'fontScale', 'color', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'image', 'hr','alignment','emoji'],
            emoji: {
                imagePath: '/static/img/emoji'
            },
            <%--upload:{--%>
                <%--url :"http://up-z1.qiniu.com/",--%>
                <%--params:{"token":"${token}"},--%>
                <%--fileKey:"file"--%>
            <%--}，--%>
            upload:{
                url : 'http://up-z1.qiniu.com/',
                params:{"token":"${token}"},
                fileKey:'file'
            },
        });

        $("#sendBtn").click(function () {
            $("#topicForm").submit();
        });
        $("#topicForm").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                title:{
                    required:true
                },
                node:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:"请填写主题"
                },
                node:{
                    required:"请选择节点"
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/editTopic",
                    type:"post",
                    data:$("#topicForm").serialize(),
                    beforeSend:function () {
                        $("#sendBtn").text("发布中...").attr("disabled","disabled");
                    },
                    success:function (data) {
                        if (data.state == "success"){

                            window.location.href="/topicDetail?topicid="+data.data;
                        }else{
                            alert("修改失败aaaa");
                        }
                    },
                    error:function () {
                        alert("服务器忙,请稍后再试");
                    },
                    complete:function () {
                        $("#sendBtn").text("发布").removeAttr("disabled");
                    }
                })
            }
        })
    });
</script>

</body>
</html>
