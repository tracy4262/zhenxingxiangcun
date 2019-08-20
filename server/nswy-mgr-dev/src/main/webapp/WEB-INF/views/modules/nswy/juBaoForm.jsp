<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>举报管理</title>
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
		<li><a href="${ctx}/nswy/getJuBaolist">举报管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/juBaoForm?expertId=${nswyGovInfo.juBaoId}">举报<%--<shiro:hasPermission name="nswy:corpInfo:edit">--%>${not empty nswyGovInfo.juBaoId?'修改':'添加'}<%--</shiro:hasPermission>--%><%--<shiro:lacksPermission name="nswy:corpInfo:view">--%>查看<%--</shiro:lacksPermission>--%></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nswyJuBao" action="${ctx}/nswy/updateJuBao" method="post" class="form-horizontal">
		<form:hidden path="juBaoId"/>
		<%--<sys:message content="${message}"/>--%>
		<div class="control-group">
			<label class="control-label">举报人:</label>
			<div class="controls">
				<form:input path="creator"  htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">举报类型:</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">举报链接:</label>
			<div class="controls">
				<form:input path="link" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="50" class="required "/>
			</div>
		</div>
		<div class="form-actions">
		<%--	<shiro:hasPermission name="nswy:corpInfo:edit">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>