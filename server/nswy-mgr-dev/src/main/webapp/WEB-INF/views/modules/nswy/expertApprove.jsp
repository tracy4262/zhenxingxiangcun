<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家审核</title>
	<meta name="decorator" content="default"/>
    <style type="text/css">
        .lightbox{
            position: fixed;
            top: 0px;
            left: 0px;
            height: 100%;
            width: 100%;
            z-index: 7;
            opacity: 0.3;
            display: block;
            background-color: rgb(0, 0, 0);
            display: none;
        }
        .pop,iframe{
            position: absolute;
            left: 50%;
            top:0;
            width: 893px;
            height: 100%;
            margin-left: -446.5px;
            z-index: 9;
        }
		img{
			height: 100px;
		}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					/* loading('正在提交，请稍等...'); */
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
</head><body>



	<form:form id="inputForm" modelAttribute="nswyExpert" action="${ctx}/nswy/updateApproveStatus" method="post" class="form-horizontal">
		<input type="hidden" name="expertId" value="${nswyExpert.expertId}" />
		<div class="control-group" style="margin-top: 10px">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<input  value="${nswyExpert.expertName}" htmlEscape="false" maxlength="50" disabled="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">专家分类:</label>
			<div class="controls">
				<input value="${nswyExpert.expertType}" htmlEscape="false" maxlength="50" disabled="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">擅长领域:</label>
			<div class="controls">
				<input  value="${nswyExpert.adeptField}" htmlEscape="false" maxlength="50" disabled="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">擅长物种:</label>
			<div class="controls">
				<input value="${nswyExpert.adeptSpecies}" htmlEscape="false" maxlength="50" disabled="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">职称:</label>
			<div class="controls">
				<input value="${nswyExpert.title}" htmlEscape="false" maxlength="50" disabled="true"/>
			</div>
		</div>

		<div class="control-group" >
			<label class="control-label">个人照片:</label>
			<div class="controls" disabled="true">
				<img src="${nswyExpert.personalPhoto}">
			</div>
		</div>

		<div class="control-group" >
			<label class="control-label">资质证书:</label>
			<div class="controls" disabled="true">
					<img src="${nswyExpert.qualificationCertificate}">
			</div>
		</div>

		<label class="control-label">审核状态:</label>
		<c:if test="${nswyExpert.approveStatus eq 0}">
		<div class="controls">
			<input type="radio" checked name="approveStatus" value="1">通过
			<input type="radio" name="approveStatus" value="2">拒绝
			<span class="help-inline"></span>
		</div>
		</c:if>


		 <div class="form-actions">
		    <c:if test="${nswyExpert.approveStatus eq 0}">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="确定" onclick="return confirm('确认要审核吗？')"/>&nbsp;
			 </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		 </div>
	</form:form>
</body>
</html>