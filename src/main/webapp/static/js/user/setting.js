/**
 * Created by loveoh on 2016/12/19.
 */
$(function () {
    $("#basicBtn").click(function () {
        $("#basicForm").submit();

    });
    $("#basicForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            email:{
                required:true,
                email:true,
                remote:"/validate/email?type=%%%"
            }
        },
        messages: {
            email: {
                required: "请输入电子邮件",
                email: "电子邮件格式不正确",
                remote: "电子有见被占用"
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/user/setting?action=profile",
                type:"post",
                data:$("#basicForm").serialize(),
                beforeSend:function () {
                    $("#basicBtn").text("保存中...").attr("disable","disable");
                },
                success:function (data) {
                    if(data.state == "success"){
                        alert("修改成功");
                    }
                },
                error:function () {
                    alert("服务器忙,请稍后再试")
                },
                complete:function () {
                    $("#basicBtn").text("保存").removeAttr("disable","disable");
                }
            });
        }

    });

    $("#passwordBtn").click(function () {

        $("#passwordForm").submit();

    });

    $("#passwordForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            oldPassword:{
                required:true,
                rangelength:[6,18]
            },
            newPassword:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#newPassword"
            }
        },
        messages:{
            oldPassword:{
                required:"请输入密码",
                rangelength:"密码长度必须为6-18位"
            },
            newPassword:{
                required:"请输入新密码",
                rangelength:"密码长度必须为6-18位"
            },
            repassword:{
                required:"请输入确认密码",
                rangelength:"密码长度必须为6-18位",
                equalTo:"输入的两次密码不相同"
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/user/setting?action=password",
                type:"post",
                data:$("#passwordForm").serialize(),
                beforeSend:function () {
                    $("#passwordBtn").text("保存中...").attr("disable","disable");
                },
                success:function (data) {
                    if (data.state == "success"){
                        alert("修改成功,请重新登录");
                        window.location.href="/login";
                    }else{
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器忙,请稍后再试");
                },
                complete:function () {
                    $("#passwordBtn").text("保存").attr("disable");
                }

            });
        }

    });
});
