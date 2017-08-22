<%--
  Created by IntelliJ IDEA.
  User: lyfai
  Date: 2017/5/21
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>雅堂快速投资助手</title>
    <meta charset="utf-8">
    <meta name="keywords" content="雅堂,雅堂投资,雅堂投资助手,雅堂快速投资助手" />
    <meta name="description" content="雅堂快速投资助手" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 插件 -->
    <link rel="stylesheet" href="/public/vendor/bootstrap-select/bootstrap-select.css">
    <link rel="stylesheet" href="/public/vendor/formvalidation/formValidation.css">
</head>

<body class="page-login layout-full page-dark">

<div class="page height-full">
    <div class="page-content height-full">
        <div class="page-brand-info vertical-align animation-slide-left hidden-xs">
            <div class="page-brand vertical-align-middle">
                <div class="brand">
                    <%--<img class="brand-img" src="/public/images/logo-white.svg" height="50" alt="Admui">--%>
                </div>
                <h3>雅堂快速投资助手</h3>
                <ul class="list-icons">
                    <li><i class="wb-check" aria-hidden="true"></i> 您可以 自动投月标。</li>
                    <li><i class="wb-check" aria-hidden="true"></i> 您可以 自动抢秒</li>
                    </li>
                </ul>
                <div>
                    <a href="https://jr.yatang.cn" class="btn btn-primary btn-outline"><i class="icon wb-home"></i> 返回官网</a>
                </div>
            </div>
        </div>
        <div class="page-login-main animation-fade">

            <div class="vertical-align">
                <div class="vertical-align-middle">
                    <h3 class="hidden-xs">登录 助手</h3>
                    <p class="hidden-xs">Admui 在线演示系统</p>
                    <div class="login-form" id="loginForm" style="width: 255px;">
                        <div class="form-group">
                            <select class="form-control" id="identity">
                                <option value="xiaxuan@admui_demo" data-password="123456">月标</option>
                                <option value="zhangzhiyuan@admui_demo" data-password="123456">秒标</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="username">用户名</label>
                            <input type="text" class="form-control" id="username" name="loginName" placeholder="用户名">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="password">密码</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="密码">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="password">支付密码</label>
                            <input type="password" class="form-control" id="payPassword" name="payPassword" placeholder="支付密码">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="username">红包使用倍数</label>
                            <input type="number" class="form-control" id="coupon" name="coupon" max="100" placeholder="红包使用倍数">
                        </div>
                        <div class="row">
                            <div class="col-sm-5">
                                <button onclick="send()" class="btn btn-primary btn-block margin-top-10">开始抢标</button>
                            </div>
                            <div class="col-sm-5">
                                <button type="submit" class="btn btn-danger btn-block margin-top-10">停止抢标</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- JS -->
<script src="/public/vendor/jquery/jquery.js"></script>
<script src="/public/vendor/bootstrap/bootstrap.js"></script>
<script src="/public/vendor/bootstrap-select/bootstrap-select.min.js"></script>
<script src="/public/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="/public/vendor/formvalidation/framework/bootstrap.min.js" data-deps="formValidation"></script>
<script src="/public/vendor/layer/layer.js"></script>
<%--<script src="/public/js/login.js"></script>--%>
<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8088/websocket");
    }
    else {
        layer.msg('当前浏览器不支持websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        layer.msg(innerHTML);;
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('username').value;
        websocket.send(message);
    }
</script>
</body>
</html>
