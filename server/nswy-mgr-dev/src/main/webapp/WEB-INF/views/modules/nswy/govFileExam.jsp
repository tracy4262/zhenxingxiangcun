<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机关资质文件审核</title>
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
    <script src="${ctxStatic}/generic/build/pdf.js" type="text/javascript"></script>
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
            getUnitPersonPictureList();
		});
        function showPdf(isShow) {
            var state = "";
            if (isShow) {
                state = "block";
            } else {
                state = "none";
            }
            var pop = document.getElementById("pop");
            pop.style.display = state;
            var lightbox = document.getElementById("lightbox");
            lightbox.style.display = state;
        }
        function close() {
            showPdf(false);
        }
        function getUnitPersonPictureList(){
            var arry = $("#unitPersonPictureList").html().split(",");
            var data = "";
            for (var i=0 ;i<arry.length;i++){
                //$("#unitPersonPicture").append($('img').attr('src',arry[i]))
				data = "<img src="+arry[i]+">"
				console.log(data)
				$("#unitPersonPicture").append("&nbsp;").append(data)
			}
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="nswyGovInfo" action="${ctx}/nswy/updateGovFile" method="post" class="form-horizontal">
		<form:hidden path="govInfoId"/>
		<%--<sys:message content="${message}"/>--%>
		<div class="control-group">
			<label class="control-label">机关名称:</label>
			<div class="controls">
				<form:input path="govName"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人姓名:</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册号:</label>
			<div class="controls">
				<form:input path="govCode" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二维码:</label>
			<div class="controls">
				<img src="${nswyGovInfo.qrCodeUrl}" >
				<%--<a href="${ctxStatic}${nswyGovInfo.qrCodeUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyGovInfo.qrCodeUrl}</a>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构代码证号:</label>
			<div class="controls">
				<form:input path="organizationCode" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事业单位法人证明:</label>
			<div class="controls">
				<a href="${ctxStatic}${nswyGovInfo.legalPersonCertificateUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyGovInfo.legalPersonCertificateUrl}</a>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人姓名:</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型:</label>
			<div class="controls">
				<form:input path="govType" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构级别:</label>
			<div class="controls">
				<form:input path="govLevel" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在位置:</label>
			<div class="controls">
				<form:input path="location" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机关logo图片:</label>
			<div class="controls">
				<img src="${nswyGovInfo.logoPictureList}" >
				<%--<a href="${ctxStatic}${nswyGovInfo.logoPictureList}"  target="pdfContainer" onclick="showPdf(true)">${nswyGovInfo.logoPictureList}</a>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事业单位法人证书图片:</label>
			<div style="display: none" id="unitPersonPictureList">${nswyGovInfo.unitPersonPictureList}</div>
			<div class="controls" id="unitPersonPicture">

				<%--<a href="${ctxStatic}${nswyGovInfo.unitPersonPictureList}"  target="pdfContainer" onclick="showPdf(true)">${nswyGovInfo.unitPersonPictureList}</a>--%>
			</div>
		</div>
		<!-- 资质文件点击进行预览 -->
		<%--<div class="control-group">--%>
			<%--<label class="control-label">资质文件:</label>--%>
			<%--<div class="controls">--%>
	        	<%--<a href="${ctxStatic}${nswyGovInfo.legalPersonCertificateUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyGovInfo.legalPersonCertificateUrl}</a>--%>
	    	<%--</div>--%>
	    <%--</div>--%>
	    <c:if test="${not empty nswyGovInfo.govInfoId}" >
			<div class="control-group">
				<label class="control-label">审核状态:</label>
				<div class="controls">
					<form:radiobuttons path="examStatus" items="${fns:getDictList('apply_exam')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"></span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
		<%--	<shiro:hasPermission name="nswy:corpInfo:edit">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return confirm('确认要审核吗？')"/>&nbsp;<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

	<!-- 显示pdf预览 -->
    <div class="lightbox" id="lightbox"></div>
    <div id="pop" class="pop" style="display: none;">
        <a href="javascript:close()" style="
            position: absolute;
            right: -90px;
            display: inline-block;
            width: 80px;
            height: 30px;
        " id="close">关闭</a>
        <iframe src="" frameborder="0" id="pdfContainer" name="pdfContainer"></iframe>
    </div>
</body>
</html>