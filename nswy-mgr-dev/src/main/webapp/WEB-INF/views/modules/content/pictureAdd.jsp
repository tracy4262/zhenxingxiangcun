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
	<li><a href="${ctx}/nswy/picture/control">图片列表</a></li>
	<li class="active"><a href="${ctx}/nswy/picture/add">图片添加</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="pictureInfo" action="${ctx}/nswy/picture/save" method="post" class="form-horizontal" enctype="multipart/form-data">
	<div class="control-group">
		<label class="control-label">上传图片:</label>
		<div class="controls">
			<input type="file" id="upload" name="file" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
		</div>
	</div>
    <div class="control-group">
        <label class="control-label">图片用途:</label>
        <div class="controls">
            <input type="radio" name="used" value="1">应用到农业要闻
            <input type="radio" name="used" value="2">应用到资讯中心
			<input type="radio" name="used" value="3">应用到商城首页
        </div>
    </div>
	<div class="control-group">
		<label class="control-label">描述:</label>
		<div class="controls">
			<textarea id="remark"></textarea>
		</div>
	</div>

	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>