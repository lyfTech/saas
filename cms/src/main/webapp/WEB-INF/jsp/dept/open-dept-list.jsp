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
<div class="page-container">
    <div class="example">
        <div class="alert alert-info" id="queryParams">
            <input type="text" class="input-text" id="code" style="width:250px" placeholder="输入部门信息"/>
            <button class="btn btn-success" onclick="cmsOpenDeptList.search()"><i class="Hui-iconfont">&#xe665;</i>搜索</button>
        </div>
        <div id="tableEventsToolbar">
            <a href="javascript:;" onclick="cmsOpenDeptList.save()" class="btn btn-primary radius"><i
                    class="Hui-iconfont">&#xe632;</i></a>
        </div>
        <table id="deptTableEvents" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="application/javascript" src="${ctx}/static/js/plugins.js"></script>
<script type="application/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    var cmsOpenDeptList = {
        url: {
            deptList: function () {
                return "${ctx}/dept/list";
            }
        },
        search: function () {
            $("#deptTableEvents").bootstrapTable("refresh");
        },
        save: function () {
            var rows = $("#deptTableEvents").bootstrapTable("getSelections");
            if (rows.length < 1) {
                layer.msg('请选择部门', {icon: 7});
                return false;
            }
            var id = rows[0].id;
            var name = rows[0].name;
            parent.$('#parentId').val(id);
            parent.$('#parentName').val(name);
            parent.layer.close(index);
        },
        init: function () {
            parent.layer.iframeAuto(index);
        }

    };

    $(function () {
        $("#deptTableEvents").bootstrapTable({
            method: 'post',
            url: cmsOpenDeptList.url.deptList(),
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
                    deptInfo: $("#code").val()
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
                field: 'name',
                align: 'center',
                title: '名称'
            }, {
                field: 'managerName',
                align: 'center',
                title: '部门经理'
            }, {
                field: 'parentName',
                align: 'center',
                title: '上级部门'
            }, {
                field: 'status',
                title: '账号状态',
                align: 'center',
                formatter: function (value, row) {
                    var context = '<span class="label label-danger" style="cursor: pointer" title="点击激活权限">冻结</span>';
                    if (value == 0) {
                        context = '<span class="label label-success" style="cursor: pointer" title="点击冻结权限">激活</span>';
                    }
                    return context;
                }
            }, {
                field: 'description',
                align: 'center',
                title: '描述'
            }
            ]
        });
    });

    $(function () {
        cmsOpenDeptList.init();
    });
</script>
</body>
</html>