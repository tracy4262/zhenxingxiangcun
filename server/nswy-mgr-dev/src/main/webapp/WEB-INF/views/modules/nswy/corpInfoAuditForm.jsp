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
		<li class="active"><a href="${ctx}/nswy/corpInfoForm?corpInfoId=${nswyCorpInfo.corpInfoId}">企业审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nswyCorpInfo" action="${ctx}/nswy/updateCorpInfo" method="post" class="form-horizontal">
		<form:hidden path="corpInfoId"/>
		<div>
			<label class="control-label">审核理由:</label>
			<div class="controls">
				<form:textarea path="remark"/>
			</div>

		</div>

		<label class="control-label">审核状态:</label>
		<c:if test="${nswyCorpInfo.approveStatus eq 0}">
			<div class="controls">
				<input type="radio" checked name="approveStatus" value="1">通过
				<input type="radio" name="approveStatus" value="2">拒绝
				<span class="help-inline"></span>
			</div>
		</c:if>
		</div>
		<div class="form-actions">
		<%--	<shiro:hasPermission name="nswy:corpInfo:edit">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return confirm('确认要审核吗？')"/>&nbsp;<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>