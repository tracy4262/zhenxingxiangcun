<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位管理</title>
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
		<a href="${ctx}/nswy/dictionaryUnit/control">单位列表</a>
	</li>
	<li class="active">
		<a >单位修改</a>
	</li>

</ul><br/>

<form:form id="inputForm"  modelAttribute="dictionaryUnit" action="${ctx}/nswy/dictionaryUnit/updateUnit" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="id"/>
    <form:hidden path="level"/>

	<div class="control-group">
		<label class="control-label">单位名称:</label>
		<div class="controls">
			<form:input  path="name" htmlEscape="false" maxlength="50" class="required"/>

		</div>
	</div>

<c:if test="${dictionaryUnit.level !=1}">

	<div class="control-group">
		<label class="control-label">计量单位:</label>
		<div class="controls">
			<form:input  path="calculateUnit"  maxlength="50" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">系数:</label>
		<div class="controls">
			<form:input  path="modulus"  maxlength="50"/>
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
</c:if>

	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>




</form:form>
</body>
</html>