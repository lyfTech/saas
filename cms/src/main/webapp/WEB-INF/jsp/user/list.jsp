<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../includes/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="white-bg">
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 管理员管理
    <span class="c-gray en">&gt;</span> 管理员列表
</nav>
<div class="page-container">
    <div class="example">
        <div class="alert alert-info" id="queryParams">
            <%--<div class="text-c">--%>
            <input type="text" class="input-text" id="userName" style="width:250px" placeholder="输入用户名称"/>
            <button class="btn btn-success" id="searchBtn"><i class="Hui-iconfont">&#xe665;</i>搜用户</button>
            <%--</div>--%>
        </div>
        <div id="tableEventsToolbar">
            <a href="javascript:;" onclick="cmsUserList.openAddUserModal()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe607;</i> 新增用户</a>
            <a href="javascript:;" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
        </div>
        <table id="exampleTableEvents" data-height="490" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="application/javascript" src="${ctx}/static/js/plugins.js"></script>
<script type="application/javascript">
    var cmsUserList = {
        url: {
            changeState: function () {
                return "${ctx}/user/changeState";
            },
            toAddUser: function () {
                return "${ctx}/user/add";
            }
        },
        changeUserState: function (id, msg) {
            layer.confirm('您确定要' + msg + '该用户吗？', {
                btn: ['确定', '再想想']
            }, function () {
                $.ajax({
                    type: "post",
                    url: cmsUserList.url.changeState(),
                    data: {id:id},
                    async: false,
                    success: function (data) {
                        if (data && data["isSuccess"]) {
                            layer.msg(msg + "用户成功", {icon: 6});
                        }else {
                            layer.msg(msg + "用户失败["+data["message"]+"]", {icon: 5});
                        }
                        $("#exampleTableEvents").bootstrapTable("refresh");
                    }
                });
            });
        },
        openModal: function (url) {
            layer.open({
                type: 2,
                title: '新增用户',
                area: ['500px', '400px'],
                fixed: false, //不固定
                shadeClose: true,
                maxmin: true,
                content: url,
                success: function(layero, index) {
                    layer.iframeAuto(index);
                }
            });
        },
        openAddUserModal: function () {
            cmsUserList.openModal(cmsUserList.url.toAddUser());
        }
    };

    $(function () {
        $("#exampleTableEvents").bootstrapTable({
            method: 'post',
            url: "${ctx}/user/list",
            search: false,
            pagination: !0,
            showRefresh: !0,
            sidePagination: "server",//服务端分页
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 20, 50],
            showColumns: !0,
            clickToSelect: true,
            iconSize: "outline",
            toolbar: "#tableEventsToolbar",
            icons: {refresh: "glyphicon-repeat", columns: "glyphicon-list"},
            queryParams: function (params) {
                return {
                    offset: params.offset,
                    limit: params.limit,
                    username: $("#userName").val()
                };
            },
            responseHandler: function (res) {
                return {
                    total: res.total,
                    rows: res.list
                };
            },
            dataType: "json",
            columns: [{
                radio: 'true'
            }, {
                field: 'userName',
                align: 'center',
                title: '用户名'
            }, {
                field: 'roleName',
                align: 'center',
                title: '所属角色'
            }, {
                field: 'realName',
                align: 'center',
                title: '真实姓名'
            }, {
                field: 'mobile',
                align: 'center',
                title: '手机号码'
            }, {
                field: 'email',
                align: 'center',
                title: '邮箱'
            }, {
                field: 'departmentName',
                align: 'center',
                title: '部门'
            }, {
                field: 'status',
                title: '账号状态',
                align: 'center',
                formatter: function (value, row) {
                    var context = '<span class="label label-danger" style="cursor: pointer" title="点击激活用户" onclick="cmsUserList.changeUserState('+row.id+', \'激活\')">冻结</span>';
                    if (value == 0){
                        context = '<span class="label label-success" style="cursor: pointer" title="点击冻结用户" onclick="cmsUserList.changeUserState('+row.id+', \'冻结\')">激活</span>';
                    }
                    return context;
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                formatter: function (value) {
                    return formatLocalTime(value, "yyyy-MM-dd hh:mm:ss");
                }
            }, {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var a = '<a style="text-decoration:none" onclick="" href="javascript:;" title="分配角色"><i class="Hui-iconfont">&#xe62b;</i></a>&nbsp;&nbsp;';
                    a += '<a style="text-decoration:none" onclick="" href="javascript:;" title="编辑用户信息"><i class="Hui-iconfont">&#xe602;</i></a>';
                    return a;
                }
            }
            ]
        });

        $("#searchBtn").click(function () {
            var userName = $("#userName");
            $("#exampleTableEvents").bootstrapTable("refresh");
        })
    });
</script>
</body>
</html>