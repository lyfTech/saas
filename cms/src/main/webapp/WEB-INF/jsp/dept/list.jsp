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
    <i class="Hui-iconfont">&#xe67f;</i> 菜单权限管理
    <span class="c-gray en">&gt;</span> 菜单权限列表
</nav>
<div class="page-container">
    <div class="example">
        <div class="alert alert-info" id="queryParams">
            <input type="text" class="input-text" id="code" style="width:250px" placeholder="输入权限信息"/>
            <button class="btn btn-success" id="searchBtn"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
        </div>
        <div id="tableEventsToolbar">
            <shiro:hasPermission name="role:add">
                <a href="javascript:;" onclick="cmsPermList.openAddModal()" class="btn btn-primary radius"
                   title="新增菜单按钮"><i class="Hui-iconfont">&#xe607;</i></a>
            </shiro:hasPermission>
        </div>
        <table id="exampleTableEvents" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="application/javascript" src="${ctx}/static/js/plugins.js"></script>
<script type="application/javascript">
    var cmsPermList = {
        url: {
            permList: function () {
                return "${ctx}/perm/list";
            },
            changeState: function () {
                return "${ctx}/perm/changeState";
            },
            toAdd: function () {
                return "${ctx}/perm/add";
            },
            toEdit: function (id) {
                return "${ctx}/perm/edit/" + id;
            }
        },
        changeState: function (id, msg) {
            layer.confirm('您确定要' + msg + '该权限吗？', {
                btn: ['确定', '再想想']
            }, function () {
                $.ajax({
                    type: "post",
                    url: cmsPermList.url.changeState(),
                    data: {id: id},
                    async: false,
                    success: function (data) {
                        if (data && data["isSuccess"]) {
                            layer.msg(msg + "权限成功", {icon: 6});
                        } else {
                            layer.msg(msg + "权限失败[" + data["message"] + "]", {icon: 5});
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
        openModal: function (url, title) {
            layer.open({
                type: 2,
                title: title,
                area: ['500px', '610px'],
                fixed: false, //不固定
                shadeClose: true,
                content: url,
                success: function (layero, index) {
                    layer.iframeAuto(index);
                }
            });
        },
        openAddModal: function () {
            cmsPermList.openModal(cmsPermList.url.toAdd(), '新增权限');
        },
        openEditModal: function (id) {
            cmsPermList.openModal(cmsPermList.url.toEdit(id), '修改权限信息');
        }
    };

    $(function () {
        $("#exampleTableEvents").bootstrapTable({
            method: 'post',
            url: cmsPermList.url.permList(),
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
                    code: $("#code").val()
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
                title: '名称'
            }, {
                field: 'code',
                align: 'center',
                title: 'code'
            }, {
                field: 'parentName',
                align: 'center',
                title: '父菜单'
            }, {
                field: 'url',
                align: 'center',
                title: 'URL'
            }, {
                field: 'icon',
                align: 'center',
                title: '菜单图标'
            }, {
                field: 'type',
                align: 'center',
                title: '类型',
                formatter: function (value, row) {
                    if (value == 1) {
                        return "菜单"
                    } else if (value == 2) {
                        return "按钮";
                    }
                    return context;
                }
            }, {
                field: 'sort',
                align: 'center',
                title: '排序'
            }, {
                field: 'status',
                title: '账号状态',
                align: 'center',
                formatter: function (value, row) {
                    var context = '<span class="label label-danger" style="cursor: pointer" title="点击激活权限" <shiro:hasPermission name="perm:state">onclick="cmsPermList.changeState(' + row.id + ', \'激活\')"</shiro:hasPermission>>冻结</span>';
                    if (value == 0) {
                        context = '<span class="label label-success" style="cursor: pointer" title="点击冻结权限" <shiro:hasPermission name="perm:state">onclick="cmsPermList.changeState(' + row.id + ', \'冻结\')"</shiro:hasPermission>>激活</span>';
                    }
                    return context;
                }
            }, {
                field: 'description',
                align: 'center',
                title: '描述'
            }, {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.id == 1) return '';
                    var a = '';
                    <shiro:hasPermission name="perm:edit">
                    a += '<a style="text-decoration:none" onclick="cmsPermList.openEditModal(\'' + row.id + '\')" href="javascript:;" title="编辑角色信息"><i class="Hui-iconfont">&#xe602;</i></a>';
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