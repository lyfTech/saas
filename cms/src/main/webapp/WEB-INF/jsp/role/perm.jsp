<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../includes/common.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="">
<article class="page-container">
    <form action="" method="post" class="form form-horizontal" id="form-admin-role-add">
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>角色名称：</label>
            <div class="formControls col-xs-9 col-sm-10">
                <input type="text" class="input-text" value="${role.name}" disabled/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-2">备注：</label>
            <div class="formControls col-xs-9 col-sm-10">
                <input type="text" class="input-text" value="${role.description}">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-9 col-sm-10 col-xs-offset-3 col-sm-offset-2">
                <button type="button" class="btn btn-primary radius" id="admin-role-save" onclick="cmsRolePerm.saveBtn()">
                    <i class="icon-ok"></i> 保存
                </button>
                <button type="submit" class="btn btn-default radius" id="role-perm-close">
                    <i class="icon-ok"></i> 取消
                </button>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-2">网站权限：</label>
            <div class="formControls col-xs-9 col-sm-10">
                <c:forEach items="${allPerm}" var="p">
                <dl class="permission-list">
                    <dt>
                        <label>
                            <input type="checkbox" value="${p.id}" name="permId" <c:if test="${hasPerm.contains(p.code)}">checked</c:if> >${p.name}</label>
                    </dt>
                    <dd>
                        <c:if test="${p.childs != null}">
                            <c:forEach items="${p.childs}" var="c">
                                <dl class="cl permission-list2">
                                    <dt>
                                        <label class="label label-primary">
                                            <input type="checkbox" value="${c.id}" name="permId" <c:if test="${hasPerm.contains(c.code)}">checked</c:if> >${c.name}
                                        </label>
                                    </dt>
                                    <dd>
                                        <c:if test="${c.childs != null}">
                                        <c:forEach items="${c.childs}" var="b">
                                            <label class="label label-info">
                                                <input type="checkbox" value="${b.id}" name="permId" <c:if test="${hasPerm.contains(b.code)}">checked</c:if> >${b.name}
                                            </label>
                                        </c:forEach>
                                        </c:if>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </c:if>
                    </dd>
                </dl>
                </c:forEach>
            </div>
        </div>
    </form>
</article>
<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="application/javascript">

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    var cmsRolePerm = {
        url: {
            saveRolePerm: function () {
                return "${ctx}/role/perm";
            }
        },
        getSelectIds: function () {
            var ids = new Array();
            var checkedBox = $("input[name='permId']:checked");
            $.each(checkedBox, function(index, item){
                ids.push($(this).val());
            });
            return ids;
        },
        saveBtn: function () {
            layer.confirm('您确定要给角色分配这些权限吗？', {
                btn: ['非常确定', '再想想']
            }, function () {
                $.ajax({
                    type: "post",
                    url: cmsRolePerm.url.saveRolePerm(),
                    data: {
                        roleId: ${role.id},
                        ids: cmsRolePerm.getSelectIds()
                    },
                    async: false,
                    beforeSubmit: function (formData, jqForm, options) {
                        layer.load();
                    },
                    success: function (data) {
                        layer.closeAll('loading');
                        if (data && data["isSuccess"]) {
                            parent.layer.msg("权限分配成功", {icon: 6});
                            parent.layer.close(index);
                        } else {
                            layer.msg("权限分配失败[" + data["message"] + "]", {icon: 5});
                        }
                        parent.$("#exampleTableEvents").bootstrapTable("refresh");
                    },
                    error: function () {
                        layer.closeAll('loading');
                    }
                });
            });
        }
    };
    $(function () {

        $(".permission-list dt input:checkbox").click(function () {
            $(this).closest("dl").find("dd input:checkbox").prop("checked", $(this).prop("checked"));
        });
        $(".permission-list2 dd input:checkbox").click(function () {
            var l = $(this).parent().parent().find("input:checked").length;
            var l2 = $(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
            if ($(this).prop("checked")) {
                $(this).closest("dl").find("dt input:checkbox").prop("checked", true);
                $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked", true);
            }
            else {
                /*if (l == 0) {
                    $(this).closest("dl").find("dt input:checkbox").prop("checked", false);
                }
                if (l2 == 0) {
                    $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked", false);
                }*/
            }
        });

    });

</script>
</body>
</html>