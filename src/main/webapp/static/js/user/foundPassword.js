/**
 * Created by loveoh on 2016/12/17.
 */
$(function () {

    $("#type").change(function () {
        var type = $(this).val();
        if ("email" == type){
            $("#typename").text("电子邮件");
        }else{
            $("#typename").text("手机号码");
        }
    });

    $("#findPasswordBtn").click(function () {
        $("#foundForm").submit();
    })

    $("#foundForm").validate({
        errorElement:"span",
        errorClass:"error-text",
        rules:{
            value:{
                required:true
            }
        },
        messages:{
            value:{
                required:"该项必填"
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/foundpassword",
                type:"post",
                data:$("#foundForm").serialize(),
                beforeSend:function () {
                    $("#findPasswordBtn").text("提交中...").attr("disable","disable");
                },
                success:function (data) {
                    if(data.state == "success"){
                        var type = $("#type").val();
                        if (type == "email"){
                            alert("请查收邮件");
                        }else{
                            //todo
                        }
                    }else{
                        alert(data.message);
                    }

                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#findPasswordBtn").text("提交中").removeAttr("disable");
                }
            })
        }
    })

});
