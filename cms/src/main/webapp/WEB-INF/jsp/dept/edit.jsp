<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../includes/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="">
<article class="page-container">
    <form class="form form-horizontal" id="form-user-add">
        <input type="hidden" name="id" value="${dept.id}"/>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">部门名称：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="请输入部门名称" id="name" name="name" value="${dept.name}"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">上级部门：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="hidden" class="input-text" id="parentId" name="parentId" value="${dept.parentId}"/>
                <div class="input-group m-b">
                    <input type="text" class="form-control" id="parentName" name="parentName"  value="${dept.parentName}" readonly>
                    <a href="#" class="input-group-addon" id="selectDeptBtn">选择</a>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">部门经理：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="hidden" class="input-text" id="manager" name="manager" value="${dept.manager}"/>
                <div class="input-group m-b">
                    <input type="text" class="form-control" id="managerName" name="parentName"  value="${dept.managerName}" readonly>
                    <a href="#" class="input-group-addon" id="selectManagerBtn">选择</a>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">排序：</label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="number" class="input-text" placeholder="请输入排序位置" id="sort" name="sort" value="${dept.sort}"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">账号状态：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <div class="radio-box i-checks">
                    <input name="status" type="radio" id="sex-1" value="0" <c:if test="${dept.status == 0}">checked</c:if> >
                    <label for="sex-1">激活</label>
                </div>
                <div class="radio-box i-checks">
                    <input type="radio" id="sex-2" name="status" value="1" <c:if test="${dept.status == 1}">checked</c:if> >
                    <label for="sex-2">冻结</label>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">描述：</label>
            <div class="formControls col-xs-9 col-sm-9">
                <textarea id="description" name="description" cols="" rows="" class="textarea" placeholder="100个字符以内"
                          dragonfly="true" onKeyUp="textarealength(this,100)">${dept.description}</textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;保存&nbsp;"
                       onclick="cmsDeptEdit.validateForm()">
                <input class="btn btn-default radius" type="button" value="&nbsp;取消&nbsp;&nbsp;"
                       onclick="cmsDeptEdit.closeWin()">
            </div>
        </div>
    </form>
</article>
<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="application/javascript">

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    var cmsDeptEdit = {
        url: {
            parentDepartment: function () {
                return "${ctx}/dept/open-dept-list";
            },
            manager: function () {
                return "${ctx}/dept/open-user-list/"+${dept.id};
            },
            cmsDeptEdit: function () {
                return "${ctx}/dept/edit";
            }
        },
        openModal: function (url, title, area) {
            layer.open({
                type: 2,
                title: title,
                area: area,
                fixed: false, //不固定
                shadeClose: true,
                moveOut: true,
                content: url,
                maxmin: true,
                success: function (layero, index) {
                    layer.iframeAuto(index);
                }
            });
        },
        openParentDepartmentModal: function () {
            cmsDeptEdit.openModal(cmsDeptEdit.url.parentDepartment(), "选择上级部门", ['500px', '510px']);
        },
        openManagerModal: function () {
            cmsDeptEdit.openModal(cmsDeptEdit.url.manager(), "选择部门经理", ['500px', '510px']);
        },
        validateForm: function () {
            $('#form-user-add').validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 3,
                        maxlength: 16
                    }
                },
                focusCleanup: true,
                success: "valid",
                submitHandler: function (form) {
                    cmsDeptEdit.submitForm(form);
                }
            });
        },
        submitForm: function (form) {
            $(form).ajaxSubmit({
                url: cmsDeptEdit.url.cmsDeptEdit(),
                type: 'post',
                dataType: 'json',
                beforeSubmit: function (formData, jqForm, options) {
                    layer.load();
                },
                success: function (data) {
                    layer.closeAll('loading');
                    if (data && data["isSuccess"]) {
                        parent.layer.msg(data.message, {icon: 6});
                        parent.$("#exampleTableEvents").bootstrapTable("refresh");
                        parent.layer.close(index);
                    } else {
                        layer.msg(data.message, {icon: 5});
                    }
                }, error: function () {
                    layer.closeAll('loading');
                }
            });
        },
        closeWin: function () {
            parent.layer.close(index);
        },
        init: function () {
            parent.layer.full(index);
            $(".i-checks").iCheck({checkboxClass: "icheckbox_square_blur", radioClass: "iradio_square-blue",});
            parent.layer.iframeAuto(index);
            $("#selectDeptBtn").on("click", function () {
                cmsDeptEdit.openParentDepartmentModal();
            });
            $("#selectManagerBtn").on("click", function () {
                cmsDeptEdit.openManagerModal();
            });
        }
    };

    $(function () {
        cmsDeptEdit.init();
    });

</script>
</body>
</html>