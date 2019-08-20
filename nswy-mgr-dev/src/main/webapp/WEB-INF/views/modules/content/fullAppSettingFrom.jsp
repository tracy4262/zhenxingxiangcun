<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<script type = “text/javascript” src = “${ctxStatic}/multiselect/dist/js/bootstrap-multiselect.js” > </script>
	<link rel = “stylesheet” href = “${ctxStatic}/multiselect/dist/css/bootstrap-multiselect.css” type = “text/css” />

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

		function goToHidden() {
            $('#imageLogo').css('display','none');
        }


        function goToHiddens() {
            $('#image-logo').css('display','none');
        }

	</script>
</head>
<body>
<ul class="nav nav-tabs">


	<li class="active"><a href="${ctx}/nswy/fullAppSetting/update">应用修改</a></li>

</ul><br/>

<form:form id="inputForm"  modelAttribute="fullAppSettingInfo" action="${ctx}/nswy/fullAppSetting/updateFullAppSetting" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="appSettingId"/>

	<div class="control-group">
		<label class="control-label">应用名称:</label>
		<div class="controls">
			<form:input  path="appName" htmlEscape="false" maxlength="50" class="required"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">应用简介:</label>
		<div class="controls">
			<form:textarea path="applicationAbstract" htmlEscape="false"  class="required"/>

		</div>
	</div>

	<c:if test="${fullAppSettingInfo.level eq 2}">
		<div class="control-group">
			<label class="control-label">适用对象:${userList}</label>
			<div class="controls">

				<select id="userType" name="userType" htmlEscape="false" class="required input-xlarge" multiple>

					<c:forEach var="report" items="${list}">
						<option value="${report.userType}" <c:if test="${report.check eq true }">selected</c:if>>
								${report.userTypeName}
						</option>
					</c:forEach>


				</select>
			</div>

		</div>

	</c:if>

	<div class="control-group">
		<label class="control-label">应用类型:</label>
		<div class="controls">
			<form:select path="level" class="input-medium">
				<form:option value="0">基础应用</form:option>
				<form:option value="1">通用应用</form:option>
				<form:option value="2">高级应用</form:option>
                <form:option value="3">服务应用</form:option>
			</form:select>
		</div>
	</div>


	<div class="control-group">
		<label class="control-label">是否免费:</label>
		<div class="controls">
			<form:select path="cost" class="input-medium">
				<form:option value="0">是</form:option>
				<form:option value="1">否</form:option>
			</form:select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">是否推荐:</label>
		<div class="controls">
			<form:select path="recommend" class="input-medium">
				<form:option value="1">是</form:option>
				<form:option value="0">否</form:option>
			</form:select>
		</div>
	</div>
	<c:if test="${fullAppSettingInfo.logo ne '' and fullAppSettingInfo.logo ne null}">
		<div class="control-group" id="imageLogo">
			<label class="control-label">已上传的应用logo:</label>
			<div class="controls">
				<img  src="${fullAppSettingInfo.logo}" style="width: 280px;"/>

			</div>
		</div>
	</c:if>


	<div class="control-group">
		<label class="control-label">应用logo:</label>
		<div class="controls">

			<input type="file" id="logo" name="file1" accept="image/png,image/gif,image/jpg" onclick="goToHidden()"/>
		</div>
	</div>

	<%--<c:if test="${fullAppSettingInfo.image ne ''and fullAppSettingInfo.logo ne null }">--%>
		<%--<div class="control-group" id="image-logo" >--%>
			<%--<label class="control-label">已上传的应用截图:</label>--%>
			<%--<div class="controls">--%>

				<%--<img src="${fullAppSettingInfo.image}" style="width: 280px;"/>--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</c:if>--%>


	<%--<div class="control-group">--%>
		<%--<label class="control-label">应用截图:</label>--%>
		<%--<div class="controls">--%>

			<%--<input type="file" id="image" name="file2" accept="image/png,image/gif,image/jpg" onclick="goToHiddens()"/>--%>
		<%--</div>--%>
	<%--</div>--%>


	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>




</form:form>
</body>
</html>