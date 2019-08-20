\<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%--<link href="${ctxStatic}/multiselect/docs/css/bootstrap-3.3.2.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/multiselect/docs/js/jquery-2.1.3.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/multiselect/docs/js/bootstrap-3.3.2.min.js" type="text/javascript"></script>--%>
	<script type = “text/javascript” src = “${ctxStatic}/multiselect/dist/js/bootstrap-multiselect.js” > </script>
	<link rel = “stylesheet” href = “${ctxStatic}/multiselect/dist/css/bootstrap-multiselect.css” type = “text/css” />

	<script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
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
	<li><a href="${ctx}/nswy/reportManage/control">报表列表</a></li>
	<li><a href="${ctx}/nswy/reportManage/add">报表添加</a></li>
	<li><a href="${ctx}/nswy/reportManageType/control">报表类型列表</a></li>
	<li class="active"><a href="${ctx}/nswy/reportManageType/add">报表类型添加</a></li>
</ul><br/>
<form:form id="inputForm"  modelAttribute="reportManageTypeInfo" action="${ctx}/nswy/reportManageType/save" method="post" class="form-horizontal" enctype="multipart/form-data">


	<div class="control-group">
		<label class="control-label">报表类型:</label>
		<div class="controls">
			<input type="text" id="reportTypeName"  name="reportTypeName" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
		</div>
	</div>


	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>




</form:form>
</body>
</html>