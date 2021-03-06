<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职务管理</title>
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
		<li class="active"><a href="${ctx}/nswy/technical/add/">职称添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="technicalTitleInfo" action="${ctx}/nswy/technical/save" method="post" class="form-horizontal">

		<input type="hidden" id="pid" name="pid" value="${pid}"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">职称类型:</label>
			<div class="controls">
				<select id="titleType" name="titleType" class="input-medium">
					<option value="0">事业单位</option>
					<option value="1">企业单位</option>
				</select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">职称名称:</label>
			<div class="controls">
				<input id="technicalTitleName" name="technicalTitleName" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>



		<div class="form-actions">
			<shiro:hasPermission name="sys:area:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>