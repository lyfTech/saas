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
    <title>在线演示系统</title>
    <meta charset="utf-8">
    <meta name="keywords" content="admui,admui官网,admui下载,admui框架,通用后台管理系统,后台框架,ui框架" />
    <meta name="description" content="Admui在线演示系统，Admui是一个基于最新Web技术的企业级通用管理系统快速开发框架，可以帮助企业极大的提高工作效率，节省开发成本，提升品牌形象。" />
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
                    <img class="brand-img" src="/public/images/logo-white.svg" height="50" alt="Admui">
                </div>
                <h3>Admui 通用管理系统快速开发框架</h3>
                <ul class="list-icons">
                    <li>
                        <i class="wb-check" aria-hidden="true"></i> Admui 是一个基于最新 Web
                        技术的企业级通用管理系统快速开发框架，可以帮助企业极大的提高工作效率，节省开发成本，提升品牌形象。
                    </li>
                    <li><i class="wb-check" aria-hidden="true"></i> 您可以 Admui 为基础，快速开发各种MIS系统，如CMS、OA、CRM、ERP、POS等。</li>
                    <li><i class="wb-check" aria-hidden="true"></i> Admui 紧贴业务特性，涵盖了大量的常用组件和基础功能，最大程度上帮助企业节省时间成本和费用开支。
                    </li>
                </ul>
                <div>
                    <a href="http://admui.com" class="btn btn-primary btn-outline"><i class="icon wb-home"></i> 返回官网</a>
                    <a href="" class="btn btn-primary btn-outline margin-left-5">联系客服</a>
                </div>
            </div>
        </div>
        <div class="page-login-main animation-fade">

            <div class="vertical-align">
                <div class="vertical-align-middle">
                    <div class="brand visible-xs text-center">
                        <%--<img class="brand-img" src="/public/images/logo.svg" height="50" alt="Admui">--%>
                    </div>
                    <h3 class="hidden-xs">登录 Admui</h3>
                    <p class="hidden-xs">Admui 在线演示系统</p>
                    <form action="/system/loginValidate" class="login-form" method="post" id="loginForm">
                        <div class="form-group">
                            <label class="sr-only" for="identity">选择身份</label>
                            <select class="form-control" id="identity">
                                <option value="">我自己</option>
                                <option data-divider="true"></option>
                                <option value="xiaxuan@admui_demo" data-password="123456">夏瑄</option>
                                <option value="zhangzhiyuan@admui_demo" data-password="123456">张致远</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="username">用户名</label>
                            <input type="text" class="form-control" id="username" name="loginName" placeholder="请输入用户名">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="password">密码</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="password">验证码</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="validCode" placeholder="请输入验证码">
                                <a class="input-group-addon padding-0 reload-vify" href="javascript:;">
                                    <img src="/system/captcha" height="40">
                                </a>
                            </div>
                        </div>
                        <div class="form-group clearfix">
                            <div class="checkbox-custom checkbox-inline checkbox-primary pull-left">
                                <input type="checkbox" id="remember" name="remember">
                                <label for="remember">自动登录</label>
                            </div>
                            <a class="pull-right collapsed" data-toggle="collapse" href="#forgetPassword" aria-expanded="false" aria-controls="forgetPassword">
                                忘记密码了？
                            </a>
                        </div>
                        <div class="collapse" id="forgetPassword" aria-expanded="true">
                            <div class="alert alert-warning alert-dismissible" role="alert">
                                请返回官网点击登录按钮找回密码
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7">
                                <button type="submit" class="btn btn-primary btn-block margin-top-10">登 录</button>
                            </div>
                            <div class="col-sm-5">
                                <a class="btn btn-outline btn-success btn-block margin-top-10" href="http://www.admui.com/?sendUrl=http%3A%2F%2Fdemo.admui.com%2Flogin#register" target="_blank">注册测试账号</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <footer class="page-copyright">
                <p>&copy; 2016
                    <a href="http://www.admui.com" target="_blank">Admui</a>
                </p>
            </footer>
        </div>
    </div>
</div>


<!-- JS -->
<script src="/public/vendor/jquery/jquery.js"></script>
<script src="/public/vendor/bootstrap/bootstrap.js"></script>
<script src="/public/vendor/bootstrap-select/bootstrap-select.min.js"></script>
<script src="/public/vendor/formvalidation/formValidation.min.js" data-name="formValidation"></script>
<script src="/public/vendor/formvalidation/framework/bootstrap.min.js" data-deps="formValidation"></script>
<script src="/public/js/login.js"></script>
</body>
</html>
