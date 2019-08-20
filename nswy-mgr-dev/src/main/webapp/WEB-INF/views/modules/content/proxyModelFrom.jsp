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

	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li>
		<a href="${ctx}/nswy/proxyModel/control">模版列表</a>
	</li>
	<li class="active">
		<a href="${ctx}/nswy/proxyModel/update">模版修改</a>
	</li>
</ul><br/>

<form:form id="inputForm"  modelAttribute="proxyModelInfo" action="${ctx}/nswy/proxyModel/updateProxyModelInfo" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="fileId"/>

	<div class="control-group">
		<label class="control-label">模版类型:</label>
		<div class="controls">

			<select id="fileType" name="fileType" htmlEscape="false"  class="required input-xlarge">
				<c:forEach var="item" items="${list}" >
					<option value="${item.fileType}" <c:if test="${item.fileType eq proxyModelInfo.fileType}">selected</c:if>>
							${item.fileTypeName}
					</option>
				</c:forEach>
			</select>

		</div>
	</div>

	<div class="control-group" id="imageLogo">
		<label class="control-label">已上传的模版文件:</label>
		<div class="controls">
			<form:input readonly="true" path="fileName" htmlEscape="false" maxlength="50" class="required"/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">模版文件:</label>
		<div class="controls">

			<input type="file"  name="file" accept="application/msword" onclick="goToHidden()"/>
		</div>
	</div>




	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>




</form:form>
</body>
</html>