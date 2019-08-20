<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职称</title>
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


	<li >
		<a href="${ctx}/nswy/dictionaryTechnical/control">职称</a>
	</li>
	<li class="active">
		<a >职称修改</a>
	</li>

</ul><br/>

<form:form id="inputForm"  modelAttribute="dictionaryTechnical" action="${ctx}/nswy/dictionaryTechnical/updateTechnical" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="id"/>

	<div class="control-group">
		<label class="control-label">职务名称:</label>
		<div class="controls">
			<form:input  path="name" htmlEscape="false" maxlength="50" class="required"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">编码:</label>
		<div class="controls">
			<form:input  path="value"  maxlength="50"/>
		</div>
	</div>

    <div class="control-group">
		<label class="control-label">说明:</label>
		<div class="controls">
			<form:textarea path="remark" />
		</div>
	</div>

	<div class="control-group">
	<label class="control-label">职称分类:</label>
	<div class="controls">
			<c:if test="${dictionaryTechnical.titleType eq 0}">
				<input type="radio" checked name="titleType" value="0">事业单位专业技术岗位
				<input type="radio" name="titleType" value="1">企业
			</c:if>
			<c:if test="${dictionaryTechnical.titleType eq 1}">
				<input type="radio" name="titleType" value="0">事业单位专业技术岗位
				<input type="radio" checked name="titleType" value="1">企业
			</c:if>
	</div>
</div>

	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>




</form:form>
</body>
</html>