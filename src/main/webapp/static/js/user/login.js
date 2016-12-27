/**
 * Created by loveoh on 2016/12/16.
 */
$(function () {

/*    获取url中redirect后面的域名*/
    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    //按回车键,直接提交表单
    $("#password").keydown(function () {
        if (event.keyCode == 13){
            $("#loginBtn").click();
        }

    })
    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    })
    $("#loginForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules: {
            username: {
                required: true,
                minlength: 3
            },
            password: {
                required: true,
                rangelength: [6, 18]
            }
        },
            messages:{
                username:{
                    required:"请输入账号",
                    minlength:"账号至少为三位字符"
                },
                password:{
                    required:"请输入密码",
                    rangelength:"密码必须大于6位小于18位"
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/login",
                    type:"post",
                    data:$("#loginForm").serialize(),
                    beforeSend:function () {
                        $("#loginBtn").text("登录中...").attr("disabled","disabled");
                    },
                    success:function (data) {
                        if (data.state == "success"){
                            swal("登录成功","","success")
                            var url = getParameterByName("redirect");
                            if (url){
                                var hash = location.hash;
                                if(hash){
                                    window.location.href=url+hash;
                                }else{
                                    window.location.href=url;
                                }
                            }else{
                                window.location.href="/home";
                            }
                        }else{
                            //alert(data.message)
                            swal(data.message,"","error");
                        }
                    },
                    error:function () {
                       alert("服务器忙,请稍后再试");
                    },
                    complete:function () {
                        $("#loginBtn").text("登录").removeAttr("disabled");
                    }
                })

            }


    });
});
