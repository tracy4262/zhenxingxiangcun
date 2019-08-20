<%@ page import="com.ovit.manager.common.config.Global" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通用商品名管理</title>
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
		<a href="${ctx}/nswy/dictionaryCommodity/control">通用商品名列表</a>
	</li>
	<li class="active">
		<a >通用商品名修改</a>
	</li>

</ul><br/>

<form:form id="inputForm"  modelAttribute="dictionaryCommodity" action="${ctx}/nswy/dictionaryCommodity/updateCommodity" method="post" class="form-horizontal" enctype="multipart/form-data">
	<form:hidden path="id"/>

	<div class="control-group">
		<label class="control-label">通用商品名名称:</label>
		<div class="controls">
			<form:input  path="name" htmlEscape="false" maxlength="50"  class="required" oninput="getPinyin($('#name').val())"/>

		</div>
	</div>

	<div class="control-group">
		<label class="control-label">通用商品名拼音:</label>
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
				<c:forEach var="report" items="${industrylist}">
					<option value="${report.industryTypeId}" <c:if test="${report.industryTypeId eq dictionaryCommodity.industryTypeId}">selected</c:if>>
							${report.industryTypeName}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

    <div class="control-group">
        <label class="control-label">产品分类:</label>
        <div class="controls">
            <select id="productTypeId" name="productTypeId" htmlEscape="false" class="input-xlarge">
				<option value=''>无</option>
                <c:forEach var="report" items="${productTypeList}">
                    <option value="${report.productTypeId}" <c:if test="${report.productTypeId eq dictionaryCommodity.productTypeId}">selected</c:if>>
                            ${report.productTypeName}
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
				<c:forEach var="report" items="${specieslist}">
					<option value="${report.fid}" <c:if test="${report.fid eq dictionaryCommodity.relatedSpeciesId}">selected</c:if>>
							${report.fname}
					</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">追溯类型:</label>
		<div class="controls">
			<form:select path="traceabilityType" class="input-xlarge">
				<form:option value="追溯">追溯</form:option>
				<form:option value="应溯">应溯</form:option>
			</form:select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">是否主要农产品:</label>
		<div class="controls">
			<input type="radio" name="type" value="1">是
			<input type="radio" name="type" value="3">否
			<span class="help-inline"></span>
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