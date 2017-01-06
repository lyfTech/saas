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
<%--<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 角色管理
    <span class="c-gray en">&gt;</span> 角色列表
</nav>--%>
<div class="page-container">
    <div class="example">
        <div class="alert alert-info" id="queryParams">
            <input type="text" class="input-text" id="rolename" style="width:250px" placeholder="输入角色信息"/>
            <button class="btn btn-success" id="searchBtn"><i class="Hui-iconfont">&#xe665;</i>搜角色</button>
        </div>
        <div id="tableEventsToolbar">
            <shiro:hasPermission name="role:add">
                <a href="javascript:;" onclick="cmsRoleList.openAddRoleModal()" class="btn btn-primary radius" title="新增角色"><i class="Hui-iconfont">&#xe607;</i></a>
            </shiro:hasPermission>
        </div>
        <table id="exampleTableEvents" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="application/javascript" src="${ctx}/static/js/plugins.js"></script>
<script type="application/javascript">
    var cmsRoleList = {
        url: {
            roleList: function () {
                return "${ctx}/role/list";
            },
            changeState: function () {
                return "${ctx}/role/changeState";
            },
            toAddRole: function () {
                return "${ctx}/role/add";
            },
            toEditRole: function (id) {
                return "${ctx}/role/edit/" + id;
            },
            toRolePerm: function (id) {
                return "${ctx}/role/perm/" + id;
            }
        },
        changeRoleState: function (id, msg) {
            layer.confirm('您确定要' + msg + '该角色吗？', {
                btn: ['确定', '再想想']
            }, function () {
                $.ajax({
                    type: "post",
                    url: cmsRoleList.url.changeState(),
                    data: {id: id},
                    async: false,
                    success: function (data) {
                        if (data && data["isSuccess"]) {
                            layer.msg(msg + "角色成功", {icon: 6});
                        } else {
                            layer.msg(msg + "角色失败[" + data["message"] + "]", {icon: 5});
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
        openAddRoleModal: function () {
            cmsRoleList.openModal(cmsRoleList.url.toAddRole(), '新增角色', ['500px', '450px']);
        },
        openEditRoleModal: function (id) {
            cmsRoleList.openModal(cmsRoleList.url.toEditRole(id), '修改角色信息', ['500px', '450px']);
        },
        openRolePermModal: function (id) {
            cmsRoleList.openModal(cmsRoleList.url.toRolePerm(id), '用户分配角色', ['60%', '100%']);
        }
    };

    $(function () {
        $("#exampleTableEvents").bootstrapTable({
            method: 'post',
            url: cmsRoleList.url.roleList(),
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
                    rolename: $("#rolename").val()
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
                field: 'name',
                align: 'center',
                title: '角色'
            }, {
                field: 'description',
                align: 'center',
                title: '角色描述'
            }, {
                field: 'sort',
                align: 'center',
                title: '排序'
            }, {
                field: 'status',
                title: '账号状态',
                align: 'center',
                formatter: function (value, row) {
                    var context = '<span class="label label-danger" style="cursor: pointer" title="点击激活用户" <shiro:hasPermission name="role:del">onclick="cmsRoleList.changeRoleState(' + row.id + ', \'激活\')"</shiro:hasPermission>>冻结</span>';
                    if (value == 0) {
                        context = '<span class="label label-success" style="cursor: pointer" title="点击冻结用户" <shiro:hasPermission name="role:del">onclick="cmsRoleList.changeRoleState(' + row.id + ', \'冻结\')"</shiro:hasPermission>>激活</span>';
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
                    <shiro:hasPermission name="role:edit">
                    a += '<a style="text-decoration:none" onclick="cmsRoleList.openEditRoleModal(\'' + row.id + '\')" href="javascript:;" title="编辑角色信息"><i class="Hui-iconfont">&#xe602;</i></a>&nbsp;&nbsp;';
                    </shiro:hasPermission>
                    <shiro:hasPermission name="role:perm">
                    a += '<a style="text-decoration:none" onclick="cmsRoleList.openRolePermModal(\'' + row.id + '\')" href="javascript:;" title="分配权限"><i class="Hui-iconfont">&#xe62b;</i></a>';
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