<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../includes/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="white-bg">
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 管理员管理
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
            <shiro:hasPermission name="user:add">
                <a href="javascript:;" onclick="cmsUserList.openAddUserModal()" class="btn btn-primary radius" title="新增用户"><i class="Hui-iconfont">&#xe607;</i></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="user:reset">
                <a href="javascript:;" onclick="cmsUserList.resetPwd()" class="btn btn-warning radius" title="批量重置密码"><i class="Hui-iconfont">&#xe66c;</i></a>
            </shiro:hasPermission>
        </div>
        <table id="exampleTableEvents" data-mobile-responsive="true"></table>
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
            },
            toEditUser: function (id) {
                return "${ctx}/user/edit/" + id;
            },
            resetPwd: function () {
                return "${ctx}/user/resetpwd";
            },
            toUserRole: function (id) {
                return "${ctx}/user/role/" + id;
            }
        },
        changeUserState: function (id, msg) {
            layer.confirm('您确定要' + msg + '该用户吗？', {
                btn: ['确定', '再想想']
            }, function () {
                $.ajax({
                    type: "post",
                    url: cmsUserList.url.changeState(),
                    data: {id: id},
                    async: false,
                    success: function (data) {
                        if (data && data["isSuccess"]) {
                            layer.msg(msg + "用户成功", {icon: 6});
                        } else {
                            layer.msg(msg + "用户失败[" + data["message"] + "]", {icon: 5});
                        }
                        $("#exampleTableEvents").bootstrapTable("refresh");
                    }
                });
            });
        },
        getSelectIds: function (rows) {
            if (rows == null && rows.length < 1) {
                return "";
            }
            var ids = new Array();
            $.each(rows, function (index, item) {
                ids.push(item.id);
            });
            console.log(JSON.stringify(ids));
            return ids;
        },
        openModal: function (url, title, area) {
            layer.open({
                type: 2,
                title: title,
                area: area,
                fixed: false, //不固定
                shadeClose: true,
                maxmin: true,
                content: url,
                success: function (layero, index) {
                    layer.iframeAuto(index);
                }
            });
        },
        openAddUserModal: function () {
            cmsUserList.openModal(cmsUserList.url.toAddUser(), '新增用户', ['500px', '450px']);
        },
        openEditUserModal: function (id) {
            cmsUserList.openModal(cmsUserList.url.toEditUser(id), '修改用户信息', ['500px', '450px']);
        },
        openUserRoleModal: function (id) {
            cmsUserList.openModal(cmsUserList.url.toUserRole(id), '用户分配角色', ['60%', '100%']);
        },
        resetPwd: function () {
            var rows = $("#exampleTableEvents").bootstrapTable("getSelections");
            if (rows.length < 1) {
                layer.msg('请选择用户', {icon: 7});
                return false;
            }
            layer.prompt({title: '输入新密码', formType: 1}, function(val, index){
                if (val == ''){
                    return false;
                }
                $.ajax({
                    type: "post",
                    url: cmsUserList.url.resetPwd(),
                    data: {
                        password: val,
                        ids: cmsUserList.getSelectIds(rows)
                    },
                    async: false,
                    success: function (data) {
                        if (data && data["isSuccess"]) {
                            layer.msg("重置用户密码成功", {icon: 6});
                        } else {
                            layer.msg("重置用户密码成功[" + data["message"] + "]", {icon: 5});
                        }
                        $("#exampleTableEvents").bootstrapTable("refresh");
                    }
                });
                layer.close(index);
            });
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
                checkbox: 'true'
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
                    var context = '';
                    context += '<span class="label label-danger" style="cursor: pointer" title="点击激活用户" <shiro:hasPermission name="user:delete">onclick="cmsUserList.changeUserState(' + row.id + ', \'激活\')"</shiro:hasPermission>冻结</span>';
                    if (value == 0) {
                        context = '<span class="label label-success" style="cursor: pointer" title="点击冻结用户" <shiro:hasPermission name="user:delete">onclick="cmsUserList.changeUserState(' + row.id + ', \'冻结\')"</shiro:hasPermission>>激活</span>';
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
                    var a = '';
                    <shiro:hasPermission name="user:edit">
                    a += '<a style="text-decoration:none" onclick="cmsUserList.openEditUserModal(\'' + row.id + '\')" href="javascript:;" title="编辑用户信息"><i class="Hui-iconfont">&#xe602;</i></a>&nbsp;&nbsp;';
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:role">
                    a += '<a style="text-decoration:none" onclick="cmsUserList.openUserRoleModal(\'' + row.id + '\')" href="javascript:;" title="分配角色"><i class="Hui-iconfont">&#xe62b;</i></a>';
                    </shiro:hasPermission>
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