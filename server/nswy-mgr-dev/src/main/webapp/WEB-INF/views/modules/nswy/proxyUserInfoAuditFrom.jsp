<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>代理审核</title>
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
		<li><a href="${ctx}/nswy/getProxyUserInfolist">代理列表</a></li>
		<li class="active"><a href="${ctx}/nswy/proxyUserInfoAuditFrom?proxyUserInfoId=${proxyUserInfo.proxyUserInfoId}">代理审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="proxyUserInfo" action="${ctx}/nswy/updateProxyUserInfo" method="post" class="form-horizontal">
		<input type="hidden" name="proxyUserInfoId" value="${proxyUserInfo.proxyUserInfoId}" />
        <div>
            <label class="control-label">审核理由:</label>
            <div class="controls">
                <form:textarea path="auditOpinion"/>
				<%--<textarea  name="auditOpinion" type="text" id="auditOpinion" value="${fn:escapeXml(proxyUserInfo.auditOpinion)}" />--%>
			</div>

        </div>
		<label class="control-label">审核状态:</label>
		<c:if test="${proxyUserInfo.status eq 2}">
			<div class="controls">
				<input type="radio" checked name="status" value="1">通过<%----%>
				<input type="radio" name="status" value="3">拒绝
				<span class="help-inline"></span>
			</div>
		</c:if>

		<div class="form-actions">
			<c:if test="${proxyUserInfo.status eq 2}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="审核" onclick="return confirm('确认要审核吗？')"/>&nbsp;
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>