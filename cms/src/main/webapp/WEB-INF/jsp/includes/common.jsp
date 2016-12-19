<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${ctx}/static/css/style.min.css?v=4.1.0" rel="stylesheet">

<link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/bootstrap-table/1.11.0/bootstrap-table.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/iCheck/1.0.2/skins/square/_all.css" rel="stylesheet">
<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/static/js/html5.js"></script>
<script type="text/javascript" src="${ctx}static/lib/respond.min.js"></script>
<script type="text/javascript" src="${ctx}static/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/plugins/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<script src="http://cdn.bootcss.com/jquery/3.0.0/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="http://cdn.bootcss.com/layer/3.0/layer.min.js"></script>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="http://cdn.bootcss.com/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"></script>
<script src="http://cdn.bootcss.com/iCheck/1.0.2/icheck.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap-table/1.11.0/bootstrap-table.js"></script>
<script src="http://cdn.bootcss.com/bootstrap-table/1.11.0/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="http://cdn.bootcss.com/pace/1.0.1/pace.min.js"></script>
<script type="text/javascript" src="http://lib.h-ui.net/jquery.validation/1.14.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://lib.h-ui.net/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="http://lib.h-ui.net/jquery.validation/1.14.0/messages_zh.min.js"></script>
<script src="http://cdn.bootcss.com/jquery.form/3.49/jquery.form.min.js"></script>
<%--<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script>--%>
<script src="${ctx}/static/js/plugins/layer/laydate/laydate.js"/>
<script type="application/javascript">
    $(document).ready(function(){
        $('input[type=checkbox],input[type=radio]').iCheck({
            checkboxClass: 'icheckbox_square_blue',
            radioClass: 'iradio_square_blue',
            increaseArea: '20%' // optional
        });
    });

    function show_tips(id, msg){
        if (msg != "" && id != "") {
            layer.tips(msg, id);
        }
    }
</script>