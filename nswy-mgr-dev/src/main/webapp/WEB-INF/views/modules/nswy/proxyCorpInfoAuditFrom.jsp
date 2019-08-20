<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理管理</title>
	<meta name="decorator" content="default"/>
	<style>

		table>tr>td {
			width: 500px;
		}

	</style>
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
		<li><a href="${ctx}/nswy/getProxyCorpInfolist">代理管理页面</a></li>
		<li class="active"><a href="${ctx}/nswy/proxyCorpInfoForm?proxyCorpInfoId=${proxyCorpInfo.proxyCorpInfoId}">代理管理审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="proxyCorpInfo" action="${ctx}/nswy/updateProxyCorpInfo" method="post" class="form-horizontal">
		<input type="hidden" name="proxyCorpInfoId" value="${proxyCorpInfo.proxyCorpInfoId}" />
		<table>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">被代理企业名称:</label>
						<div class="controls">
							<form:input path="corpName" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">企业名称:</label>
						<div class="controls">
							<form:input path="corpName1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">经营范围:</label>
						<div class="controls">
							<form:input path="businessScope" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">经营范围:</label>
						<div class="controls">
							<form:input path="businessScope1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">统一社会信用代码:</label>
						<div class="controls">
							<form:input path="creditCode" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">统一社会信用代码:</label>
						<div class="controls">
							<form:input path="creditCode1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">法人姓名:</label>
						<div class="controls">
							<form:input path="legalPerson" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">法人姓名:</label>
						<div class="controls">
							<form:input path="legalPerson1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">身份证号:</label>
						<div class="controls">
							<form:input path="identificationCard" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">身份证号:</label>
						<div class="controls">
							<form:input path="identificationCard1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">身份证照片:</label>
						<div class="controls">
							<c:forEach items="${fn:split(proxyCorpInfo.identificationCardUrl,',')}" var="proxy" >
								<img style="width: 450px;height: 260px;" src="${proxy}" />
							</c:forEach>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">身份证照片:</label>
						<div class="controls">
							<c:forEach items="${fn:split(proxyCorpInfo.identificationCardUrl1,',')}" var="proxy" >
								<img style="width: 450px;height: 260px;" src="${proxy}" />
							</c:forEach>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">注册资本:(万元)</label>
						<div class="controls">
							<form:input path="registeredCapital" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">注册资本:(万元)</label>
						<div class="controls">
							<form:input path="registeredCapital1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">成立日期:</label>
						<div class="controls">
							<form:input path="establishDate" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">成立日期:</label>
						<div class="controls">
							<form:input path="establishDate1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">营业期限:</label>
						<div class="controls">
							<input value="${fn:replace(proxyCorpInfo.busnissTerm,',' ,'-' )}" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">营业期限:</label>
						<div class="controls">
							<input value="${fn:replace(proxyCorpInfo.busnissTerm1,',' ,'-' )}" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">所处位置:</label>
						<div class="controls">
							<form:input path="location" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">所处位置:</label>
						<div class="controls">
							<form:input path="location1" readonly="true" htmlEscape="false" />
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">详细地址:</label>
						<div class="controls">
							<form:input path="addrDetail" readonly="true" htmlEscape="false"  />
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">详细地址:</label>
						<div class="controls">
							<form:input path="addrDetail1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">企业类型:</label>
						<div class="controls">
							<form:input path="companyType" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">企业类型:</label>
						<div class="controls">
							<form:input path="companyType1" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">企业工商营业执照:</label>
						<div class="controls">
							<c:forEach items="${fn:split(proxyCorpInfo.businessLicenseUrl, ',')}" var="url">
								<img style="width: 450px;height: 260px;" src="${url}"/>
							</c:forEach>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">企业工商营业执照:</label>
						<div class="controls">
							<c:forEach items="${fn:split(proxyCorpInfo.businessLicenseUrl1, ',')}" var="url">
								<img style="width: 450px;height: 260px;" src="${url}"/>
							</c:forEach>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">企业住所:</label>
						<div class="controls">
							<form:input path="companyAddress" readonly="true" htmlEscape="false"  class="required"/>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">企业住所:</label>
						<div class="controls">
							<form:input path="companyAddress1" readonly="true" htmlEscape="false"  class="required"/>
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
			<tr >
				<td colspan="2">
					<div>
						<label class="control-label">审核状态:</label>
						<c:if test="${proxyCorpInfo.approveStatus eq 0}">
							<div class="controls">
								<input type="radio" checked name="approveStatus" value="1">通过
								<input type="radio" name="approveStatus" value="2">拒绝
								<span class="help-inline"></span>
							</div>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<div class="form-actions">
						<c:if test="${proxyCorpInfo.approveStatus eq 0}">
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