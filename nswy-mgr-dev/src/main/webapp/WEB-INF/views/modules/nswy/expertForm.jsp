<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
		<li><a href="${ctx}/nswy/getExpertlist">专家管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/expertForm?expertId=${nswyExpert.expertId}">专家<%--<shiro:hasPermission name="nswy:corpInfo:edit">--%>${not empty nswyExpert.expertId?'修改':'添加'}<%--</shiro:hasPermission>--%><%--<shiro:lacksPermission name="nswy:corpInfo:view">--%>查看<%--</shiro:lacksPermission>--%></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nswyExpert" action="${ctx}/nswy/updateExpert" method="post" class="form-horizontal">
		<form:hidden path="expertId"/>
		<%--<sys:message content="${message}"/>--%>
		<div class="control-group">
			<label class="control-label">专家姓名:</label>
			<div class="controls">
				<form:input path="expertName"  htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属单位:</label>
			<div class="controls">
				<form:input path="organaztion" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业:</label>
			<div class="controls">
				<form:input path="industry" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">擅长领域:</label>
			<div class="controls">
				<form:input path="field" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职称:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="form-actions">
		<%--	<shiro:hasPermission name="nswy:corpInfo:edit">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>