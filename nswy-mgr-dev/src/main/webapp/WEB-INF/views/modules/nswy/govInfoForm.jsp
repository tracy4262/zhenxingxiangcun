<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机关组织机构管理</title>
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
		<li><a href="${ctx}/nswy/getGovInfolist">机关组织机构管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/govInfoForm?expertId=${nswyGovInfo.govInfoId}">机关<shiro:hasPermission name="sys:govinfo:view">${not empty nswyGovInfo.govInfoId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:govinfo:view">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nswyGovInfo" action="${ctx}/nswy/updateGovInfo" method="post" class="form-horizontal">
		<form:hidden path="govInfoId"/>
		<%--<sys:message content="${message}"/>--%>
		<div class="control-group">
			<label class="control-label">机关名称:</label>
			<div class="controls">
				<form:input path="govName"  htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册号:</label>
			<div class="controls">
				<form:input path="organizationCode" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址:</label>
			<div class="controls">
				<form:input path="location" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="form-actions">
	<%--	<shiro:hasPermission name="sys:govinfo:view">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>