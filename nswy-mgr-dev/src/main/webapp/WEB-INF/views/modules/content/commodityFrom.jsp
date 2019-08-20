<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>菜单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    //loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li >
        <a href="${ctx}/nswy/commodity/control">通用商品</a>
    </li>

    <li class="active">
        <a href="${ctx}/nswy/generalService/add">通用商品修改</a>
    </li>
</ul><br/>

<form:form id="inputForm"  modelAttribute="commodityInfo" action="${ctx}/nswy/commodity/updateCommodity" method="post" class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="commodityId"/>

    <div class="control-group">
        <label class="control-label">通用商品名称:</label>
        <div class="controls">
            <form:input  path="name" htmlEscape="false" maxlength="50"  class="required"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">别名:</label>
        <div class="controls">
            <div><form:input  path="alias" htmlEscape="false" maxlength="50" /></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">备注:</label>
        <div class="controls">
            <div><form:input  path="remark" htmlEscape="false" maxlength="50"/></div>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>

</form:form>
</body>
</html>