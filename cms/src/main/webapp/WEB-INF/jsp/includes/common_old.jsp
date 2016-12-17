<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="shortcut icon" href="${ctx}/static/img/favicon.ico">
<link href="${ctx}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${ctx}/static/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="${ctx}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<link href="${ctx}/static/css/style.min.css?v=4.1.0" rel="stylesheet">
<link href="http://cdn.bootcss.com/iCheck/1.0.2/skins/square/_red.css" rel="stylesheet">

<script src="${ctx}/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctx}/static/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${ctx}/static/js/plugins/layer/layer.js"></script>
<script src="${ctx}/static/js/content.min.js?v=1.0.0"></script>
<script src="${ctx}/static/js/demo/layer-demo.min.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/hplus.min.js?v=4.1.0"></script>
<script src="${ctx}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
<script src="${ctx}/static/js/plugins/iCheck/icheck.min.js"></script>

<script type="application/javascript">
    function show_tips(id, msg) {
        if (msg != "" && id != "") {
            layer.tips(msg, id);
        }
    }

    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>