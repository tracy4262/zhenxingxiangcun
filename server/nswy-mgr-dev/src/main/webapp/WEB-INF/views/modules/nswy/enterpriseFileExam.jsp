<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业资质文件审核</title>
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
            identificationCardUrl();
            businessLicense();
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
        function identificationCardUrl() {
			var arr = $("#identificationCard").html().split(",");
			var data = "";
			for (var i = 0;i<arr.length;i++){
			    data = "<img src="+arr[i]+">";
			    $("#identificationCardUrl").append("&nbsp;").append(data)
			}
        }
        function businessLicense() {
			var arry = $("#businessLicenseList").html().split(",");
			var data = "";
			for (var i = 0;i<arry.length;i++){
			    data = "<img src="+arry[i]+">";
			    $("#businessLicense").append("&nbsp;").append(data);
			}
        }
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="nswyCorpInfo" action="${ctx}/nswy/updateEnterprise" method="post" class="form-horizontal">
		<form:hidden path="corpInfoId"/>
		<%--<sys:message content="${message}"/>--%>
		<div class="control-group">
			<label class="control-label">企业名称:</label>
			<div class="controls">
				<form:input path="corpName"  htmlEscape="false" maxlength="50" class="r	equired" disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">企业标识:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="avatar"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">经营范围:</label>
			<div class="controls">
				<form:input path="businessScope"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">统一社会信用代码:</label>
			<div class="controls">
				<form:input path="creditCode"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">二维码:</label>--%>
			<%--<div class="controls">--%>
				<%--<img src="${nswyCorpInfo.qrCodeUrl}" style="height: 100px;">--%>
				<%--&lt;%&ndash;<a href="${ctxStatic}${nswyCorpInfo.qrCodeUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.qrCodeUrl}</a>&ndash;%&gt;--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">营业执照副本:</label>--%>
			<%--<div class="controls">--%>
				<%--<img src="${nswyCorpInfo.businessLicense}" style="height: 100px;">--%>
				<%--&lt;%&ndash;<a href="${ctxStatic}${nswyCorpInfo.businessLicense}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.businessLicense}</a>&ndash;%&gt;--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机:</label>
			<div class="controls">
				<form:input path="phone"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱:</label>
			<div class="controls">
				<form:input path="email"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">运营者:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="operator"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">身份证照片:</label>
			<div class="controls" id="identificationCardUrl">
				<div id="identificationCard" style="display: none">${nswyCorpInfo.identificationCardUrl}</div>
				<%--<img src="${nswyCorpInfo.identificationCardUrl}" style="height: 100px;">--%>
				<%--<a href="${ctxStatic}${nswyCorpInfo.identificationCardUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.identificationCardUrl}</a>--%>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">身份证反面照片:</label>--%>
			<%--<div class="controls">--%>
				<%--<img src="${nswyCorpInfo.identificationCardNegtiveUrl}" style="height: 100px;">--%>
				<%--&lt;%&ndash;<a href="${ctxStatic}${nswyCorpInfo.identificationCardNegtiveUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.identificationCardNegtiveUrl}</a>&ndash;%&gt;--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">法人介绍:</label>
			<div class="controls">
				<form:input path="legalPersonIntroduce"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业法人照片:</label>
			<div class="controls">
				<img src="${nswyCorpInfo.legalPersonUrl}" style="height: 100px;">
				<%--<a href="${ctxStatic}${nswyCorpInfo.legalPersonUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.legalPersonUrl}</a>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册资本:</label>
			<div class="controls">
				<form:input path="registeredCapital"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成立日期:</label>
			<div class="controls">
				<form:input path="establishDate"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业期限:</label>
			<div class="controls">
				<form:input path="busnissTerm"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业类型:</label>
			<div class="controls">
				<form:input path="industryType"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所处位置:</label>
			<div class="controls">
				<form:input path="location"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司类型:</label>
			<div class="controls">
				<form:input path="companyType"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关物种:</label>
			<div class="controls">
				<form:input path="relatedSpecies"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关产品:</label>
			<div class="controls">
				<form:input path="relatedProduct"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关服务:</label>
			<div class="controls">
				<form:input path="relatedService"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司简介:</label>
			<div class="controls">
				<form:input path="companyProfile"  htmlEscape="false" maxlength="50" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传企业LOGO:</label>
			<div class="controls">
				<img src="${nswyCorpInfo.logoUrl}" style="height: 100px;">
				<%--<a href="${ctxStatic}${nswyCorpInfo.logoUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.logoUrl}</a>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业工商营业执照:</label>
			<div class="controls" id="businessLicense" id="businessLicense">
				<%--<img src="${nswyCorpInfo.businessLicenseUrl}" style="height: 100px;">--%>
				<div id="businessLicenseList" style="display: none">${nswyCorpInfo.businessLicenseUrl}</div>
				<%--<a href="${ctxStatic}${nswyCorpInfo.businessLicenseUrl}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.businessLicenseUrl}</a>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人姓名:</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工商执照注册号:</label>
			<div class="controls">
				<form:input path="corpCode" htmlEscape="false" maxlength="50" disabled="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号:</label>
			<div class="controls">
				<form:input path="identificationCard" htmlEscape="false" maxlength="50" disabled="true"/>
			</div>
		</div>
		<!-- 资质文件点击进行预览 -->
		<%--<div class="control-group">--%>
			<%--<label class="control-label">资质文件:</label>--%>
			<%--<div class="controls">--%>
	        	<%--<a href="${ctxStatic}${nswyCorpInfo.businessLicense}"  target="pdfContainer" onclick="showPdf(true)">${nswyCorpInfo.businessLicense}</a>--%>
	    	<%--</div>--%>
	    <%--</div>--%>
	    <c:if test="${not empty nswyCorpInfo.corpInfoId}" >
			<div class="control-group">
				<label class="control-label">审核状态:</label>
				<div class="controls">
					<form:radiobuttons path="examStatus" items="${fns:getDictList('apply_exam')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"></span>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
		<%--	<shiro:hasPermission name="nswy:corpInfo:edit">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;<%--</shiro:hasPermission>--%>
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