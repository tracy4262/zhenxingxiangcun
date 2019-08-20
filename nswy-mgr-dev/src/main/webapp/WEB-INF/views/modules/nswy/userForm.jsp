<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li><a href="${ctx}/nswy/getUserlist">用户管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/form?userId=${nswyUser.userId}">用户<shiro:lacksPermission name="sys:dict:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nswyUser" action="${ctx}/nswy/saveOrUpdateUser" method="post" class="form-horizontal">
		<div class="control-group">
            <label class="control-label">账号:</label>
            <div class="controls">
               ${nswyUser.loginAccount}
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">昵称:</label>
			<div class="controls">
				${nswyUser.displayName}
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">签名:</label>
            <div class="controls">
                ${nswyUser.signature}
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<img src="${nswyUser.avatar}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<c:choose>
                <c:when test="${empty nswyUser.userType or nswyUser.userType eq 0}">
                  个人用户
                </c:when>
                <c:when test="${nswyUser.userType eq 1}">
                企业
                </c:when>
                <c:when test="${nswyUser.userType eq 2}">
                    政府
                </c:when>
				<c:when test="${nswyUser.userType eq 4}">
					专家
				</c:when>
                </c:choose>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>