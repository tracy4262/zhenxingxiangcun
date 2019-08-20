<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用服务名管理</title>
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
		<li class="active"><a href="${ctx}/nswy/shopCurrencyService/shopCurrencyServiceInfoFrom?shopId=${shopCurrencyServiceInfo.shopId}">通用服务名审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopCurrencyServiceInfo" action="${ctx}/nswy/shopCurrencyService/currencyAdopt" method="post" class="form-horizontal">
		<input type="hidden" name="shopId" value="${shopCurrencyServiceInfo.shopId}" />
        <div>
            <label class="control-label">理由:</label>
            <div class="controls">
                <form:textarea path="refuseReason"/>
            </div>

        </div>
		<label class="control-label">审核状态:</label>
		<c:if test="${shopCurrencyServiceInfo.approveStatus eq 0}">
			<div class="controls">
				<input type="radio" name="approveStatus" value="1">通过
				<input type="radio" name="approveStatus" value="2">拒绝
				<span class="help-inline"></span>
			</div>
		</c:if>
		</div>

		<div class="form-actions">
			<c:if test="${shopCurrencyServiceInfo.approveStatus eq 0}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="确定" onclick="return confirm('确认要审核吗？')"/>&nbsp;
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>