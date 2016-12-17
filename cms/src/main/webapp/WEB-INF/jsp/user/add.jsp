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
            <label class="form-label col-xs-3 col-sm-3">管理员：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="请输入" id="userName" name="userName"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">初始密码：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="password" class="input-text" autocomplete="off" placeholder="请输入密码" id="password" name="password"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">账号状态：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <div class="radio-box i-checks">
                    <input name="sex" type="radio" id="sex-1" checked>
                    <label for="sex-1">激活</label>
                </div>
                <div class="radio-box i-checks">
                    <input type="radio" id="sex-2" name="sex">
                    <label for="sex-2">冻结</label>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">职能部门：</label>
            <div class="formControls col-xs-9 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="adminRole" size="1">
				<option value="0">超级管理员</option>
				<option value="1">总编</option>
				<option value="2">栏目主辑</option>
				<option value="3">栏目编辑</option>
			</select>
			</span></div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">手机号码：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" value="" placeholder="" id="phone" name="phone">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-3">邮箱：<span class="c-red">*</span></label>
            <div class="formControls col-xs-9 col-sm-9">
                <input type="text" class="input-text" placeholder="@" name="email" id="email">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;保存&nbsp;" onclick="addUser.sumbmitForm()">
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
        url: {},
        sumbmitForm: function () {
            $('#form-user-add').validate({
                rules:{
                    userName:{
                        required:true,
                        minlength:4,
                        maxlength:16
                    },
                    password:{
                        required:true,
                    },
                    phone:{
                        isPhone:true,
                    },
                    email:{
                        email:true,
                    }
                },
                focusCleanup:true,
                success:"valid",
                submitHandler:function(form){
                    $(form).ajaxSubmit();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.$('.btn-refresh').click();
                    parent.layer.close(index);
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