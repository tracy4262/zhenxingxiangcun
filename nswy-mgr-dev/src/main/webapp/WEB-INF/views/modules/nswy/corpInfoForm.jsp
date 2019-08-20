<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					closeLoading();
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
		<li><a href="${ctx}/nswy/getCorpInfolist">企业管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/corpInfoForm?corpInfoId=${nswyCorpInfo.corpInfoId}">企业<%--<shiro:hasPermission name="nswy:corpInfo:edit">--%>${not empty nswyCorpInfo.corpInfoId?'修改':'添加'}<%--</shiro:hasPermission>--%><%--<shiro:lacksPermission name="nswy:corpInfo:view">--%>查看<%--</shiro:lacksPermission>--%></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nswyCorpInfo" action="${ctx}/nswy/updateCorpInfo" method="post" class="form-horizontal">
		<form:hidden path="corpInfoId"/>
		<%--<sys:message content="${message}"/>--%>
		<div class="control-group">
			<label class="control-label">企业名称:</label>
			<div class="controls">
				<form:input path="corpName"  htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人姓名:</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工商执照注册号:</label>
			<div class="controls">
				<form:input path="creditCode" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号:</label>
			<div class="controls">
				<form:input path="identificationCard" htmlEscape="false" maxlength="50" class="required abc"/>
			</div>
		</div>
		<div class="form-actions">
	      <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>