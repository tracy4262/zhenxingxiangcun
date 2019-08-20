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
           /* honorCertificatePicturpaeList();
            qualificationCertificatePictureList();
            personalPictureList();*/
		});

	</script>
</head><body>
	<form:form id="inputForm" modelAttribute="nswyExpert" action="${ctx}/nswy/updateExpertApply" method="post" class="form-horizontal">
		<input type="hidden" name="expertId" value="${nswyExpert.id}" />
		<div class="control-group">
			<label class="control-label">专家姓名:</label>
			<div class="controls">${nswyExpert.expertName}</div>
		</div>
		<div class="control-group">
			<label class="control-label">专家类型:</label>
			<div class="controls">${nswyExpert.expertType}</div>
		</div>
		<div class="control-group">
			<label class="control-label">账号:</label>
			<div class="controls">${nswyExpert.loginAccount}</div>
		</div>
		<div class="control-group">
			<label class="control-label">擅长领域:</label>
			<div class="controls">${nswyExpert.adeptField}</div>
		</div>
		<div class="control-group">
			<label class="control-label">擅长物种:</label>
			<div class="controls">${nswyExpert.adeptSpecies}</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关物种:</label>
			<div class="controls">${nswyExpert.relatedSpecies}</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关行业:</label>
			<div class="controls">${nswyExpert.relatedIndustry}</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关商品:</label>
			<div class="controls">${nswyExpert.relatedProduct}</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关服务:</label>
			<div class="controls">${nswyExpert.relatedService}</div>
		</div>
		<div class="control-group">
			<label class="control-label">荣誉证书:</label>
			<div class="controls" id="honorCertificatePicture"></div>
		</div>
		<div class="control-group">
			<label class="control-label">资质证书:</label>
			<div class="controls" id="qualificationCertificatePicture"></div>
		</div>
		<div class="control-group">
			<label class="control-label">个人照片:</label>
			<div class="controls" id="personalPicture">
			</div>
			</div>
		 <div class="form-actions">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		 </div>
	</form:form>
</body>
</html>