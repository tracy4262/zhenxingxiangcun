<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					closeLoading();
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
		<li><a href="${ctx}/nswy/getProxyGovInfolist">代理管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/proxyGovInfoForm?proxyGovInfoId=${proxyGovInfo.proxyGovInfoId}">代理管理审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="proxyGovInfo" action="${ctx}/nswy/updateProxyGovInfo" method="post" class="form-horizontal">
		<input type="hidden" name="proxyGovInfoId" value="${proxyGovInfo.proxyGovInfoId}" />
	<table>
		<tr>
			<td>
				<div class="control-group">
					<c:if test="${proxyGovInfo.countryType == '3'}">
						<label class="control-label">被代理机关名称:</label>
					</c:if>
					<c:if test="${proxyGovInfo.countryType == '5'}">
						<label class="control-label">被代理乡村名称:</label>
					</c:if>

					<div class="controls">
						<form:input path="govName" readonly="true" htmlEscape="false"  class="required"/>
					</div>
				</div>
			</td>

			<td>
				<div class="control-group">
					<c:if test="${proxyGovInfo.countryType == '3'}">
						<label class="control-label">代理机关名称:</label>
					</c:if>
					<c:if test="${proxyGovInfo.countryType == '5'}">
						<label class="control-label">代理乡村名称:</label>
					</c:if>

					<div class="controls">
						<form:input path="govName1" readonly="true" htmlEscape="false"  class="required"/>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<label class="control-label">统一社会信用代码:</label>
				<div class="controls">
					<form:input path="organizationCode" readonly="true" htmlEscape="false"  class="required"/>
				</div>
				</div>
			</td>
			<td>
				<label class="control-label">统一社会信用代码:</label>
				<div class="controls">
					<form:input path="organizationCode1" readonly="true" htmlEscape="false"  class="required"/>
				</div>
				</div>

			</td>
		</tr>
		<c:if test="${proxyGovInfo.countryType == '3'}">
			<tr>
				<td>
					<div class="control-group">

						<label class="control-label">机构类型:</label>
						<div class="controls">
							<form:input path="govType" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">

						<label class="control-label">机构类型:</label>
						<div class="controls">
							<form:input path="govType1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">机构级别:</label>
						<div class="controls">
							<form:input path="govLevel" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">机构级别:</label>
						<div class="controls">
							<form:input path="govLevel1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>


		</c:if>

		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">行政区划:</label>
					<div class="controls">
						<form:input path="location" readonly="true" htmlEscape="false"  class="required"/>
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">行政区划:</label>
					<div class="controls">
						<form:input path="location1" readonly="true" htmlEscape="false"  class="required"/>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">详细地址:</label>
					<div class="controls">
						<form:input path="addrDetail" readonly="true" htmlEscape="false" />
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">详细地址:</label>
					<div class="controls">
						<form:input path="addrDetail1" readonly="true" htmlEscape="false" />
					</div>
				</div>
			</td>
		</tr>
        <c:if test="${proxyGovInfo.countryType == '3'}">
		<tr>
			<td>
				<div class="control-group">
					<label class="control-label">社会信用代码证:</label>
					<div class="controls">
						<img style="width: 450px;height: 260px;" src="${proxyGovInfo.qualificationCertificatePictureList}" />
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<label class="control-label">社会信用代码证:</label>
					<div class="controls">
						<img style="width: 450px;height: 260px;" src="${proxyGovInfo.qualificationCertificatePictureList1}" />
					</div>
				</div>
			</td>
		</tr>
        </c:if>
		<tr>
			<td>
				<div class="control-group">
					<c:if test="${proxyGovInfo.countryType == '3'}">
						<label class="control-label">事业单位法人证书图片:</label>
					</c:if>
					<c:if test="${proxyGovInfo.countryType == '5'}">
						<label class="control-label">基层群众性自治组织特别法人统一社会信用代码证书:</label>
					</c:if>
					<div class="controls">
						<img style="width: 450px;height: 260px;" src="${proxyGovInfo.unitPersonPictureList}" />
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<c:if test="${proxyGovInfo.countryType == '3'}">
						<label class="control-label">事业单位法人证书图片:</label>
					</c:if>
					<c:if test="${proxyGovInfo.countryType == '5'}">
						<label class="control-label">基层群众性自治组织特别法人统一社会信用代码证书:</label>
					</c:if>
					<div class="controls">
						<img style="width: 450px;height: 260px;" src="${proxyGovInfo.unitPersonPictureList1}" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="control-group">
					<c:if test="${proxyGovInfo.countryType == '3'}">
						<label class="control-label">机关住所:</label>
					</c:if>
					<c:if test="${proxyGovInfo.countryType == '5'}">
						<label class="control-label">乡村地址:</label>
					</c:if>

					<div class="controls">
						<form:input path="address" readonly="true" htmlEscape="false"  class="required"/>
					</div>
				</div>
			</td>
			<td>
				<div class="control-group">
					<c:if test="${proxyGovInfo.countryType == '3'}">
						<label class="control-label">机关住所:</label>
					</c:if>
					<c:if test="${proxyGovInfo.countryType == '5'}">
						<label class="control-label">乡村地址:</label>
					</c:if>

					<div class="controls">
						<form:input path="address1" readonly="true" htmlEscape="false"  class="required"/>
					</div>
				</div>
			</td>
		</tr>
		<tr >
			<td colspan="2">
				<div>
					<label class="control-label">审核理由:</label>
					<div class="controls">
						<form:textarea path="remark"/>
					</div>

				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<label class="control-label">审核状态:</label>
				<c:if test="${proxyGovInfo.approveStatus eq 0}">
					<div class="controls">
						<input type="radio" checked name="approveStatus" value="1">通过
						<input type="radio" name="approveStatus" value="2">拒绝
						<span class="help-inline"></span>
					</div>
				</c:if>

			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<div class="form-actions">
					<c:if test="${proxyGovInfo.approveStatus eq 0}">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="审核" onclick="return confirm('确认要审核代理吗？')"/>&nbsp;
					</c:if>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</td>
		</tr>
	</table>

	</form:form>
</body>
</html>