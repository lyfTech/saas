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
            <input type="text" class="input-text" id="rolename" style="width:250px" placeholder="输入角色信息"/>
            <button class="btn btn-success" onclick="cmsUserRoleList.searchBtn()"><i class="Hui-iconfont">&#xe665;</i>搜角色</button>
        </div>
        <div id="tableEventsToolbar">
                <a href="javascript:;" onclick="cmsUserRoleList.saveBtn()" class="btn btn-primary radius" title="保存"><i class="Hui-iconfont">&#xe632;</i></a>
                <a href="javascript:;" onclick="cmsUserRoleList.closeWin()" class="btn btn-default radius" title="取消"><i class="Hui-iconfont">&#xe6dd;</i></a>
        </div>
        <table id="userRoleTable" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="application/javascript" src="${ctx}/static/js/plugins.js"></script>
<script type="application/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    var cmsUserRoleList = {
        url: {
            userRoleList: function () {
                return "${ctx}/role/list";
            },
            saveUserRole: function () {
                return "${ctx}/user/role";
            }
        },
        getSelectIds: function (rows) {
            if (rows == null && rows.length < 1){
                return "";
            }
            var ids = new Array();
            $.each(rows, function(index, item){
                ids.push(item.id);
            });
            return ids;
        },
        searchBtn: function () {
            $("#userRoleTable").bootstrapTable("refresh");
        },
        closeWin: function () {
            parent.layer.close(index);
        },
        saveBtn: function () {
            var rows = $("#userRoleTable").bootstrapTable("getSelections");
            if (rows.length < 1) {
                layer.msg('请选择一条数据', {icon: 7});
                return false;
            }
            if (rows.length > 2) {
                layer.msg('最多只能给用户分配两个角色', {icon: 7});
                return false;
            }
            layer.confirm('您确定要给用户分配这些角色吗？', {
                btn: ['非常确定', '再想想']
            }, function () {
                $.ajax({
                    type: "post",
                    url: cmsUserRoleList.url.saveUserRole(),
                    data: {
                        userId: ${userId},
                        ids: cmsUserRoleList.getSelectIds(rows)
                    },
                    async: false,
                    beforeSubmit: function (formData, jqForm, options) {
                        layer.load();
                    },
                    success: function (data) {
                        layer.closeAll('loading');
                        if (data && data["isSuccess"]) {
                            parent.layer.msg("角色分配成功", {icon: 6});
                            parent.layer.close(index);
                        } else {
                            layer.msg("角色分配失败[" + data["message"] + "]", {icon: 5});
                        }
                        parent.$("#userRoleTable").bootstrapTable("refresh");
                    },
                    error: function () {
                        layer.closeAll('loading');
                    }
                });
            });
        },
        init: function () {
//            parent.layer.full(index);
        }
    };

    $(function () {
        cmsUserRoleList.init();

        $("#userRoleTable").bootstrapTable({
            method: 'post',
            url: cmsUserRoleList.url.userRoleList(),
            search: false,
            pagination: true,
            showRefresh: true,
            sidePagination: "server",//服务端分页
            pageNumber: 1,
            pageSize: 100,
            showColumns: true,
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
                checkbox: 'true',
                formatter: function (value, row, index) {
                    <c:forEach items="${userRole}" var="role">
                    if (row.id == ${role.id}){
                        return {disabled: false, checked: true}
                    }
                    </c:forEach>
                }
            }, {
                field: 'name',
                align: 'center',
                title: '角色'
            }, {
                field: 'description',
                align: 'center',
                title: '角色描述'
            }, {
                field: 'status',
                title: '账号状态',
                align: 'center',
                formatter: function (value, row) {
                    return value == 0?'激活':'冻结';
                }
            }]
        });
    });
</script>
</body>
</html>