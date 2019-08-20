<%@ page import="com.ovit.manager.common.config.Global" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用服务名管理</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">

        function getPinyin(chinese){
            $.ajax({
                url:"<%= Global.getConfig("api.url")%>/wiki/api/species/getSpeciesPinYin/"+chinese,
                data:{

                },
                type:"GET",
                dataType: "JSON",
                success : function (data) {
                    $("#pinyin").val(data.data)
                }
            });
        }

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
	<li >
		<a href="${ctx}/nswy/dictionaryGeneralService/control">通用服务名列表</a>
	</li>
	<li class="active">
		<a >通用服务名修改</a>
	</li>

</ul><br/>

<form:form id="inputForm"  modelAttribute="dictionaryGeneralService" action="${ctx}/nswy/dictionaryGeneralService/updateGeneralService" method="post" class="form-horizontal" enctype="multipart/form-data">

	<div class="control-group">
		<label class="control-label">通用服务名名称:</label>
		<div class="controls">
			<form:input  path="name" htmlEscape="false" maxlength="50"  class="required" oninput="getPinyin($('#name').val())"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">通用服务名拼音:</label>
		<div class="controls">
			<form:input  path="pinyin" htmlEscape="false"  maxlength="50" readonly="true"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">俗名别名:</label>
		<div class="controls">
			<form:input  path="alias" htmlEscape="false" maxlength="50" />

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">编码:</label>
		<div class="controls">
			<form:input  path="value"  maxlength="50"/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">行业分类:</label>
		<div class="controls">
			<select id="industryTypeId" name="industryTypeId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${industryList}">
					<option value="${report.industryTypeId}" <c:if test="${report.industryTypeId eq dictionaryGeneralService.industryTypeId}">selected</c:if>>
							${report.industryTypeName}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

    <div class="control-group">
        <label class="control-label">服务分类:</label>
        <div class="controls">
            <select id="serviceTypeId" name="serviceTypeId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
                <c:forEach var="report" items="${serviceTypeList}">
                    <option value="${report.serviceTypeId}" <c:if test="${report.serviceTypeId eq dictionaryGeneralService.serviceTypeId}">selected</c:if>>
                            ${report.serviceTypeName}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

	<div class="control-group">
		<label class="control-label">关联物种:</label>
		<div class="controls">
			<select id="relatedSpeciesId" name="relatedSpeciesId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${speciesList}">
					<option value="${report.fid}" <c:if test="${report.fid eq dictionaryGeneralService.relatedSpeciesId}">selected</c:if>>
							${report.fname}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">关联服务:</label>
		<div class="controls">
			<select id="serviceId" name="serviceId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${serviceList}">
					<option value="${report.serviceId}" <c:if test="${report.serviceId eq dictionaryGeneralService.serviceId}">selected</c:if>>
							${report.serviceName}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">关联产品:</label>
		<div class="controls">
			<select id="productId" name="serviceId" productId="false" class="input-xlarge">
				<option value=''>无</option>
				<c:forEach var="report" items="${productList}">
					<option value="${report.productId}" <c:if test="${report.productId eq dictionaryGeneralService.productId}">selected</c:if>>
							${report.productName}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

    <div class="control-group">
        <label class="control-label">服务对象:</label>
        <div class="controls">
            <form:input  path="servicePeopleName" htmlEscape="false" maxlength="50" />

        </div>
    </div>

    <div class="control-group">
		<label class="control-label">说明:</label>
		<div class="controls">
			<form:textarea path="remark" />
		</div>
	</div>

	<div class="form-actions">
		<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>

</form:form>
</body>
</html>