<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理管理</title>
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
		<li><a href="${ctx}/nswy/getAgencylist?userType=${userType}">代理管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/agencyForm?agencyAuditId=${agencyAudit.agencyAuditId}">代理管理审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="agencyAudit" action="${ctx}/nswy/updateAgencyAudit" method="post" class="form-horizontal">
		<input type="hidden" name="agencyAuditId" value="${agencyAudit.agencyAuditId}" />
		<input type="hidden" name="userType" value="${agencyAudit.userType}" />
		<label class="control-label">审核状态:</label>
		<c:if test="${agencyAudit.auditStatus eq 0}">
			<div class="controls">
				<input type="radio" checked name="auditStatus" value="1">通过
				<input type="radio" name="auditStatus" value="2">拒绝
				<span class="help-inline"></span>
			</div>
		</c:if>
		</div>

		<div class="form-actions">
			<c:if test="${agencyAudit.auditStatus eq 0}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="审核" onclick="return confirm('确认要审核吗？')"/>&nbsp;
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>