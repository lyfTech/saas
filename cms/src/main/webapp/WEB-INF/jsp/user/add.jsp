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
            <label class="form-label col-xs-3 col-sm-3">用户名：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="请输入登录用户名" id="userName" name="userName"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">初始密码：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="password" class="input-text" autocomplete="off" placeholder="请输入密码" id="password"
                       name="password"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">真实姓名：</label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="请输入真实姓名" id="realName" name="realName"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">账号状态：<span class="c-red">*</span></label>
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
            <label class="form-label col-xs-3 col-sm-3">职能部门：</label>
            <div class="formControls col-xs-9 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="departmentId" size="1"></select>
			</span></div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">手机号码：</label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="18888888888" id="mobile" name="mobile">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">邮箱：</label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="abc@zyx.com" name="email" id="email">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;保存&nbsp;"
                       onclick="addUser.validateForm()">
                <input class="btn btn-default radius" type="button" value="&nbsp;取消&nbsp;&nbsp;"
                       onclick="addUser.closeWin()">
            </div>
        </div>
    </form>
</article>
<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="application/javascript">

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    var addUser = {
        url: {
            getDepatment: function () {
                return "${ctx}/dept/getAll";
            },
            addUser: function () {
                return "${ctx}/user/doAdd";
            }
        },
        validateForm: function () {
            $('#form-user-add').validate({
                rules: {
                    userName: {
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    },
                    password: {
                        required: true,
                    },
                    mobile: {
                        isPhone: true,
                    },
                    email: {
                        email: true,
                    }
                },
                focusCleanup: true,
                success: "valid",
                submitHandler: function (form) {
                    addUser.submitForm(form);
                }
            });
        },
        submitForm: function (form) {
            /*$.ajax({
             url: addUser.url.addUser(),
             type: "POST",
             async: false,
             success: function (data) {
             if (data && data["status"]) {
             layer.msg(data.message, {icon: 6});
             parent.$('.btn-refresh').click();
             parent.layer.close(index);
             } else {
             layer.msg(data.message, {icon: 5});
             }
             }
             })*/
            $(form).ajaxSubmit({
                url: addUser.url.addUser(),
                type: 'post',
                contentType: 'application/json;charset=UTF-8',
                beforeSubmit: function (formData, jqForm, options) {
                    console.log(JSON.stringify(options.data));
                    layer.load();
                },
                success: function (data) {
                    if (data && data["status"]) {
                        layer.msg(data.message, {icon: 6});
                        parent.$('.btn-refresh').click();
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
            $(".i-checks").iCheck({checkboxClass: "icheckbox_square_blur", radioClass: "iradio_square-blue",})
            parent.layer.iframeAuto(index);
        }
    };

    $(function () {
        addUser.init();
    });

</script>
</body>
</html>