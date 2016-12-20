/**
 * Created by loveoh on 2016/12/17.
 */
$(function () {
    $("#resetBtn").click(function () {
        $("#resetForm").submit();
    });

    $("#resetForm").validate({
        errorElement:"span",
        errorClass:"error-text",
        rules: {
            password: {
                required: true
            },
            repassword: {
                required: true,
                equalTo: "#password"
            }
        },
        messages:{
                password:{
                    required:"请输入密码"
                },
                repassword:{
                    required:"请输入密码",
                    equalTo:"两次密码不一致"
                }
            },
        submitHandler:function () {
                $.ajax({
                    url:"/user/resetpassword",
                    type:"post",
                    data:$("#resetForm").serialize(),
                    beforeSend:function () {
                        $("#resetBtn").text("重置中...").attr("disable","disable");
                    },
                    success:function (data) {
                        if (data.state == "success"){
                            alert("密码重置成功,请重新登录");
                            window.location.href="/login";
                        }else{
                            alert(data.message);
                        }
                    },
                    error:function () {
                        alert("服务器忙,稍后再试");
                    },
                    complete:function () {
                        $("#resetBtn").text("重置").removeAttr("disable");
                    }
                });
            }
    });
});
