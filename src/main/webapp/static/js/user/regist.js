
$(function () {

    $("#btn").click(function () {
        $("#registForm").submit();
    });

    $("#registForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true,
                minlength:3,
                remote:"/validate/user"
            },
            password:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                equalTo:"#password"
            },
            email:{
                required:true,
                email:true,
                remote:"/validate/email"
            },
            phone:{
                required:true,
                digits:true,
                rangelength:[11,11]
            }

        },
        messages:{
            username:{
                required:"请输入账号",
                minlength:"账号最少为三位",
                remote:"账号已被占用"
            },
            password:{
                required:"请输入密码",
                rangelength:"密码必须是6-18位"
            },
            repassword:{
                required:"请输入密码",
                equalTo:"两次密码不一致"

            },
            email:{
                required:"请输入电子邮件",
                email:"电子邮件格式不正确",
                remote:"电子邮件被占用"
            },
            phone:{
                required:"请输入手机号",
                digits:"请输入正确的手机号",
                rangelength:"手机号必须为11位"
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/regist",
                type:"post",
                data:$("#registForm").serialize(),
                beforeSend:function () {
                    $("#btn").text("注册中...").attr("disabled","disabled");
                },
                success:function (data) {
                    alert("注册成功,请先激活账号");
                    window.location.href="/login";
                },
                error:function () {
                    alert("服务器异常,请稍后再试~~~")
                },
                complete:function () {
                    $("#btn").text("注册").removeAttr("disabled");
                }
                

            })
            
        }
    })
});