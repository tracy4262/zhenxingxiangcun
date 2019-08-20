<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
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
		<a href="${ctx}/nswy/proxyModel/control">模版列表</a>
	</li>
	<li class="active">
		<a href="${ctx}/nswy/proxyModel/add">模版添加</a>
	</li>
</ul><br/>

<form:form id="inputForm"   action="${ctx}/nswy/proxyModel/save" method="post" class="form-horizontal" enctype="multipart/form-data">

	<div class="control-group">
	<label class="control-label">模版类型:</label>
	<div class="controls">
		<select id="fileType" name="fileType" htmlEscape="false" class="required input-xlarge" >
			<c:forEach var="file" items="${list}">
				<option value="${file.fileType}">
						${file.fileTypeName}
				</option>
			</c:forEach>

		</select>

	</div>
	</div>

	<div class="control-group">
		<label class="control-label">模版文件:</label>
		<div class="controls">

			<input type="file"  name="file" accept="application/msword"/>
		</div>
	</div>




	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>




</form:form>
</body>
</html>