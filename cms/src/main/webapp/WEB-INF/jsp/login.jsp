<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="includes/common.jsp" %>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理系统 - 登录</title>
    <link href="${ctx}/static/css/animate.min.css" rel="stylesheet">
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