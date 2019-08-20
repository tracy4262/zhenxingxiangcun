\<%@ page contentType="text/html;charset=UTF-8" %>
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
            var key=$("#reportTypeId").val();
            //根据值让option选中
            $("#reportType option[value='"+key+"']").attr("selected","selected");

            var type=$("#userTypeId").val();
            //根据值让option选中
            $("#userType option[value='"+type+"']").attr("selected","selected");

        });

		function goToHidden() {
            $('#reportFileNameId').css('display','none');
        }


	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/nswy/reportManage/control">报表列表</a></li>
	<li class="active"><a href="${ctx}/nswy/reportManage/update">报表修改</a></li>
	<li><a href="${ctx}/nswy/reportManageType/control">报表类型列表</a></li>
	<li><a href="${ctx}/nswy/reportManageType/add">报表类型添加</a></li>
</ul><br/>

<form:form id="inputForm"  modelAttribute="reportManageInfo" action="${ctx}/nswy/reportManage/updateReport" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="reportId"/>
	<div class="control-group">
		<label class="control-label">报表类型:</label>
		<div class="controls">
			<input type="hidden" id ="reportTypeId" value="${reportManageInfo.reportType}"/>
			<select id="reportType" name="reportType" htmlEscape="false"  class="required input-xlarge">
				<c:forEach var="item" items="${reportTypeList}" >
					<option value="${item.reportType}" <c:if test="${item.reportType eq reportManageInfo.reportType}">selected</c:if>>
							${item.reportTypeName}
					</option>
				</c:forEach>
			</select>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">适用对象:</label>
		<div class="controls">
			<input type="hidden" id ="userTypeId" value="${reportManageInfo.userType}"/>
			<select id="userType" name="userType" htmlEscape="false" class="required input-xlarge">
				<c:forEach var="report" items="${list}">
					<option value="${report.userType}" <c:if test="${report.userType eq reportManageInfo.userType}">selected</c:if>>
							${report.userTypeName}
					</option>
				</c:forEach>

			</select>

		</div>
	</div>

	<div>

	</div>

	<div class="control-group">
		<label class="control-label">报表名称:</label>
		<div class="controls">
			<form:input path="reportName" htmlEscape="false" maxlength="50" class="required"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">报表说明:</label>
		<div class="controls">
			<form:textarea path="reportRemark" htmlEscape="false" maxlength="100" class="required"/>

		</div>
	</div>


	<div class="control-group" id="reportFileNameId">
		<label class="control-label">已上传上传模版文件:</label>
		<div class="controls">
			<form:input readonly="true" path="reportFileName" htmlEscape="false" maxlength="50" class="required"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">上传模版:</label>
		<div class="controls">
			<input type="file" id="upload" name="file" accept="application/msword" onclick="goToHidden()"/>
		</div>
	</div>




	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>




</form:form>
</body>
</html>