\<%@ page contentType="text/html;charset=UTF-8" %>
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

<form:form id="inputForm"  action="${ctx}/nswy/startImportExcel" method="post" class="form-horizontal" enctype="multipart/form-data">
	<div class="control-group">
		<label class="control-label">文件上传:</label>
		<div class="controls">
			<input type="file" id="upload" name="file" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
		</div>
	</div>

	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="导入"/>&nbsp;</shiro:hasPermission>

	</div>
</form:form>
</body>
</html>