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
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">权限名称：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="请输入权限名称" id="name" name="name"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">Code：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="请输入权限代码" id="code" name="code"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">父菜单：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <span class="select-box">
			        <select class="select" id="parentId" name="parentId" size="1">
                        <option value="">请选择</option>
                        <c:forEach items="${menus}" var="item">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select>
			    </span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">类型：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <span class="select-box">
			        <select class="select" id="type" name="type" size="1">
                        <option value="">请选择</option>
                        <c:forEach items="${permTypeEnumss}" var="item">
                            <option value="${item.key}">${item.value}</option>
                        </c:forEach>
                    </select>
			    </span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">Url:</label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="请输入菜单链接" id="url" name="url"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">图标：</label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" id="icon" name="icon"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">排序：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="number" class="input-text" placeholder="请输入排序位置" id="sort" name="sort" value="0"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">状态：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <div class="radio-box i-checks">
                    <input name="status" type="radio" id="sex-1" value="0" checked>
                    <label for="sex-1">激活</label>
                </div>
                <div class="radio-box i-checks">
                    <input type="radio" id="sex-2" name="status" value="1">
                    <label for="sex-2">冻结</label>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">描述：</label>
            <div class="formControls col-xs-9 col-sm-9">
                <textarea id="description" name="description" cols="" rows="" class="textarea" placeholder="100个字符以内" dragonfly="true" onKeyUp="textarealength(this,100)"></textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;保存&nbsp;" onclick="cmsPermAdd.validateForm()">
                <input class="btn btn-default radius" type="button" value="&nbsp;取消&nbsp;&nbsp;" onclick="cmsPermAdd.closeWin()">
            </div>
        </div>
    </form>
</article>
<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="application/javascript">

    $.validator.addMethod("refReq", function (value, element, params) {
        if ($(params[0]).val() == params[1]){
            if (value == null || $.trim(value) == ''){
                return false;
            }
        }
        return true;
    }, "这是必填字段");

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var cmsPermAdd = {
        url: {
            addPerm: function () {
                return "${ctx}/perm/add";
            }
        },
        validateForm: function () {
            $('#form-user-add').validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 2,
                        maxlength: 10
                    },
                    code: {
                        required: true,
                        minlength: 2,
                        maxlength: 30
                    },
                    parentId: {
                        required: true
                    },
                    url: {
                        refReq: ["#type", 1],
                        minlength: 2,
                        maxlength: 30
                    },
                    type: {
                        required: true
                    },
                    sort: {
                        number: true,
                        min: 1,
                        max: 999999
                    },
                    description: {
                        maxlength: 100
                    }
                },
                focusCleanup: true,
                success: "valid",
                submitHandler: function (form) {
                    cmsPermAdd.submitForm(form);
                }
            });
        },
        submitForm: function (form) {
            $(form).ajaxSubmit({
                url: cmsPermAdd.url.addPerm(),
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
            $(".i-checks").iCheck({checkboxClass: "icheckbox_square_blur", radioClass: "iradio_square-blue",});
            parent.layer.iframeAuto(index);
        }
    };

    $(function () {
        cmsPermAdd.init();
    });

</script>
</body>
</html>