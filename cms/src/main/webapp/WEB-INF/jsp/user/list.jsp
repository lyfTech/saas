<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../includes/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>账号管理</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <div class="example-wrap">
                            <div class="example">
                                <div class="alert alert-info" id="queryParams" role="alert"></div>
                                <div class="btn-group hidden-xs" id="tableEventsToolbar" role="group">
                                    <div class="btn btn-primary" data-toggle="modal" data-target="#addModal">添加记录</div>
                                </div>

                                <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                    &times;
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel">添加记录</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form role="form" action="javascript:void(0)">
                                                    <div class="form-group">
                                                        <input type="text" class="form-control" id="name" placeholder="请输入名称">
                                                    </div>
                                                    <div class="form-group">
                                                        <input type="text" class="form-control" id="age" placeholder="请输入年龄">
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                                <button type="button" class="btn btn-primary" id="addRecord">提交</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <table id="exampleTableEvents" data-height="448" data-mobile-responsive="true"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script type="application/javascript">
    $(function(){
        $("#exampleTableEvents").bootstrapTable({
            method: 'post',
            url: "${ctx}/user/list",
            search: !0,
            pagination: !0,
            showRefresh: !0,
            sidePagination: "server",//服务端分页
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 20, 50],
            showColumns: !0,
            iconSize: "outline",
            toolbar: "#tableEventsToolbar",
            icons: {refresh: "glyphicon-repeat", columns: "glyphicon-list"},
            queryParams : function(params) {
                return {
                    offset: params.offset,
                    limit: params.limit,
                };
            },
            dataType: "json",
            columns: [{
                field: 'id',
                title: 'ID',
                checkbox: 'true'
            }, {
                field: 'userName',
                title: '用户名'
            }, {
                field: 'rolsName',
                title: '所属角色'
            }, {
                field: 'realName',
                title: '真实姓名'
            }, {
                field: 'mobile',
                title: '手机号码'
            }, {
                field: 'email',
                title: '邮箱'
            }, {
                field: 'departmentName',
                title: '部门'
            }, {
                field: 'status',
                title: '账号状态',
                formatter:function(value){
                    return value == 0 ? "活跃":"冻结";
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter:function(value){
                    return new Date(value).format('yyyy-MM-dd h:m:s');
                }
            }, {
                field: 'id',
                title: '操作',
                align: 'center',
                formatter:function(value,row,index){
                    var a = '<a href="" >测试</a>';
                    return a;
                }
            }
            ]
        });
    });
</script>
</body>
</html>