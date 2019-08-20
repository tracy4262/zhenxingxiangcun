<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产出产品的物种生长环境</title>
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


	<li>
		<a href="${ctx}/nswy/dictionaryEnvironment/control">产出产品的物种生长环境</a>
	</li>
	<li class="active">
		<a >产出产品的物种生长环境添加</a>
	</li>

</ul><br/>

<form:form id="inputForm" modelAttribute="dictionaryEnvironment"  action="${ctx}/nswy/dictionaryEnvironment/save" method="post" class="form-horizontal" enctype="multipart/form-data">

	<input type="hidden" id="pid" name="pid" value="${pid}"/>
	<input type="hidden" id="level" name="level" value="${level}"/>

	<div class="control-group">
		<label class="control-label">类别名称:</label>
		<div class="controls">
			<input type="text" id="name"  name="name" htmlEscape="false" maxlength="50" class="required input-xlarge"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">编码:</label>
		<div class="controls">
			<input type="text" id="value"  name="value"  maxlength="50" />

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">说明:</label>
		<div class="controls">
			<textarea  id="remark"  name="remark" ></textarea>

		</div>
	</div>

	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>


</form:form>
</body>
</html>