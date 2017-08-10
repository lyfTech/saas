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
    <!-- 移动设备 viewport -->
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui">
    <meta name="author" content="admui.com">
    <!-- 360浏览器默认使用Webkit内核 -->
    <meta name="renderer" content="webkit">
    <!-- 禁止百度SiteAPP转码 -->
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <!-- Chrome浏览器添加桌面快捷方式（安卓） -->
    <link rel="icon" type="image/png" href="/public/images/favicon.png">
    <meta name="mobile-web-app-capable" content="yes">
    <!-- Safari浏览器添加到主屏幕（IOS） -->
    <link rel="icon" sizes="192x192" href="/public/images/apple-touch-icon.png">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Admui">
    <!-- Win8标题栏及ICON图标 -->
    <link rel="apple-touch-icon-precomposed" href="/public/images/apple-touch-icon.png">
    <meta name="msapplication-TileImage" content="/public/images/app-icon72x72@2x.png">
    <meta name="msapplication-TileColor" content="#62a8ea">
    <!-- 样式 -->
    <link rel="stylesheet" href="/public/themes/classic/base/css/site.css" id="siteStyle">
    <!--[if lte IE 9]>
    <meta http-equiv="refresh" content="0; url='http://www.admui.com/ie/'" />
    <![endif]-->
    <!--[if lt IE 10]>
    <script src="/public/vendor/media-match/media.match.min.js"></script>
    <script src="/public/vendor/respond/respond.min.js"></script>
    <![endif]-->

    <!-- 自定义 -->
    <link rel="stylesheet" href="/public/css/login.css">
    <!-- 插件 -->
    <link rel="stylesheet" href="/public/vendor/animsition/animsition.css">
    <!-- 图标 -->
    <link rel="stylesheet" href="/public/fonts/web-icons/web-icons.css">
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
                            <input type="password" class="form-control" id="password" name="password" placeholder="支付密码">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="username">红包使用倍数</label>
                            <input type="text" class="form-control" id="username" name="loginName" placeholder="红包使用倍数">
                        </div>
                        <div class="row">
                            <div class="col-sm-5">
                                <button type="submit" class="btn btn-primary btn-block margin-top-10">开始抢标</button>
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
<%--<script src="/public/js/login.js"></script>--%>
</body>
</html>
