
$(function () {
    var loadNotify = function () {
        var isLogin = $("#isLogin").text();
        //判断是否登录,用户登录之后才会调用轮询方法,
        if (isLogin == "1"){
            setInterval(loadNotify,10*1000);
        }
        $.post("/notify",function (json) {
            if (json.state == "success" && json.data > 0){
                $("#unreadnotify").text(json.data);
            }
        })
    }
    loadNotify();



});