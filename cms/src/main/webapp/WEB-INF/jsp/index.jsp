<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="includes/common.jsp" %>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <title>CRM后台 - 主页</title>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${currUser.realName}</strong></span>
                                <span class="text-muted text-xs block">${currUser.rolsName}<b class="caret"></b></span>
                                </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="J_menuItem" href="form_avatar.html">修改头像</a>
                            </li>
                            <li><a class="J_menuItem" href="profile.html">个人资料</a>
                            </li>
                            <li><a class="J_menuItem" href="contacts.html">联系我们</a>
                            </li>
                            <li><a class="J_menuItem" href="mailbox.html">信箱</a>
                            </li>
                            <li class="divider"></li>
                            <li><a href="login.html">安全退出</a>
                            </li>
                        </ul>
                    </div>
                </li>

            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">

        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="login.jsp">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/system/user" frameborder="0" data-id="getAll" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2016-2018 <a href="http://www.mei.com/" target="_blank">mei.com</a>
            </div>
        </div>
    </div>
</div>
<script type="application/javascript">
    $(function () {
        $.ajax({
            async: false,
            type: "POST",
            url: '${ctx}/getMenu',
            dataType: 'json',
            success: function (json) {
                var ul = $("#side-menu");
                var li = '';
                var data = json.resource;
                $.each(data, function (i) {
                    li += '<li><a href="#"><i class="fa fa-home"></i><span class="nav-label">' + data[i].name + '</span><span class="fa arrow"></span></a>';
                    li += '<ul class="nav nav-second-level collapse">';
                    var jdata = data[i].childs;
                    $.each(jdata, function (j) {
                        li += '<li><a href="${ctx}' + jdata[j].url + '" class="J_menuItem">' + jdata[j].name + '</a>';
                        // 三级菜单， 暂时不用
                        /*var kdata = jdata[j].childs;
                        if (jdata[j].childs != null){
                            li += '<ul class="nav nav-third-level collapse">';
                            $.each(kdata, function (k) {
                                li += '<li><a href="$ctx}' + kdata[k].url + '" class="J_menuItem">' + kdata[k].name + '</a></li>';
                            });
                            li += '</ul>';
                        }
                        li += '</li>';*/
                    });
                    li += '</ul></li>';
                });
                ul.append(li);
                ul.metisMenu(li);
            }
        });

    });
</script>
<!-- 这个JS只能放这，点击左侧菜单才能在右侧tab显示，否则会替代当前页面 -->
<script src="${ctx}/static/js/contabs.min.js"></script>
</body>

</html>