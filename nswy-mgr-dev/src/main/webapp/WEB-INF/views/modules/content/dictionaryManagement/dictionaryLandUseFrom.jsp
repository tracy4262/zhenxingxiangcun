<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>土地管理</title>
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
		<a href="${ctx}/nswy/dictionaryLandUse/control">职务列表</a>
	</li>
	<li class="active">
		<a >职务修改</a>
	</li>

</ul><br/>

<form:form id="inputForm"  modelAttribute="dictionaryLandUse" action="${ctx}/nswy/dictionaryLandUse/updateLandUse" method="post" class="form-horizontal" enctype="multipart/form-data">
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
        <label class="control-label">土地类型:</label>
        <div class="controls">
            <c:if test="${dictionaryLandUse.type eq 1}">
                <input type="radio" checked name="type" value="1">农用地
                <input type="radio" name="type" value="2">建设用地
                <input type="radio" name="type" value="3"> 未利用地
            </c:if>
            <c:if test="${dictionaryLandUse.type eq 2}">
                <input type="radio" name="type" value="1">农用地
                <input type="radio" checked name="type" value="2">建设用地
                <input type="radio" name="type" value="3"> 未利用地
            </c:if>
            <c:if test="${dictionaryLandUse.type eq 3}">
                <input type="radio" name="type" value="1">农用地
                <input type="radio" name="type" value="2">建设用地
                <input type="radio" checked name="type" value="3"> 未利用地
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