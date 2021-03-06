<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理系统 - 登录</title>
    <link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.min.css?v=4.1.0" rel="stylesheet">
    <link href="http://cdn.bootcss.com/animate.css/3.5.2/animate.min.css" rel="stylesheet">
    <!--[if lt IE 9]
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated">
    <div>
        <div>
            <h1 class="logo-name">MEI</h1>
        </div>
        <h3>欢迎使用</h3>

        <form id="loginForm" class="m-t" role="form" action="${ctx}/doLogin" method="post">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="用户名" id="userName" name="userName" autofoucus required="true" title="请输入用户名">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" id="password" name="password" required="true" title="请输入密码">
            </div>
            <button type="button" id="loginBtn" class="btn btn-primary block full-width m-b">登 录</button>
        </form>
    </div>
</div>
<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="http://cdn.bootcss.com/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"></script>
<script src="http://cdn.bootcss.com/pace/1.0.1/pace.min.js"></script>
<script src="http://cdn.bootcss.com/layer/3.0/layer.min.js"></script>
<script type="text/javascript">
    var $loginForm = $("#loginForm");
    var $username = $("#userName");
    var $password = $("#password");
    $username.focus();
    document.onkeydown = function (event) {
        var e = event ? event : (window.event ? window.event : null);
        if (e.keyCode == 13) {
            login();
        }
    }

    $("#loginBtn").click(function () {
        login();
    });

    function show_tips(id, msg){
        if (msg != "" && id != "") {
            layer.tips(msg, id);
        }
    }

    function login() {
        $.ajax({
            url: $loginForm.attr("action"),
            type: "POST",
            data: {
                username: $username.val(),
                password: $password.val()
            },
            dataType: "json",
            cache: false,
            success: function (message) {
                if (message.type == "success") {
                    $.cookie('login', 1, {path: '/'});
                    var url = message.redirectUrl;
                    if (!/^http/.test(url) && /^http/.test('${ctx}')) {
                        url = '${ctx}' + url;
                    }
                    location.href = url;
                } else {
                    show_tips('#userName', message.content);
                }
            }
        });
    }

</script>
</body>
</html>